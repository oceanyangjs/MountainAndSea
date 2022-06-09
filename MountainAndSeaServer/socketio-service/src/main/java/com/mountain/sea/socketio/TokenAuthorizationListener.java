package com.mountain.sea.socketio;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import com.mountain.sea.core.utils.Constants;
import com.mountain.sea.core.utils.JwtUtils;
import com.mountain.sea.core.utils.RedisUtils;
import com.mountain.sea.core.utils.RsaKeyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/8 19:32
 */
public class TokenAuthorizationListener implements AuthorizationListener, CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthorizationListener.class);
    private RedisUtils redisUtils;
    private PublicKey publicKey;

    public TokenAuthorizationListener(RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
    }

    @Override
    public boolean isAuthorized(HandshakeData handshakeData) {
//        todo 暂时关闭redis连接的校验
        if(1 == 1){
            return true;
        }
        String token = handshakeData.getSingleUrlParam(Constants.TOKEN_KEY);
        if (StringUtils.isEmpty(token)) {
            return false;
        }
        if(publicKey==null){
            return false;
        }
        if (JwtUtils.isTokenExpired(token, publicKey)) {
            return false;
        }
        Map<String, Object> userInfo = JwtUtils.getUserInfoFromToken(token, publicKey);
        String userKey = Constants.REDIS_USER_TOKEN_KEY_PREFIX.concat(String.valueOf(userInfo.get(Constants.CONTEXT_USER_ID)));
        String redisToken = (String) redisUtils.get(userKey);
        if (redisToken == null) {
            return false;
        }
        if (!token.equals(redisToken)) {
            return false;
        }

        return true;
    }

    private PublicKey getPublicKey(byte[] bytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            String s = redisUtils.get(Constants.REDIS_USER_PUB_KEY).toString();
            this.publicKey = getPublicKey(RsaKeyUtils.toBytes(s));
            logger.info("====== publicKey Success Achieve！ ======");
            logger.info("====== publicKey 成功获取！ ======");
        } catch (Exception e) {
            logger.error("****** init pubKey failed！******", e);
            logger.error("****** 初始化加载用户pubKey失败！ ******", e);
        }
    }
}
