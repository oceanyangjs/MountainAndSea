package com.mountain.sea.config;

import com.mountain.sea.core.utils.AECUtil;
import com.mountain.sea.core.utils.Constants;
import com.mountain.sea.core.utils.RedisUtils;
import com.mountain.sea.core.utils.RsaKeyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-07 11:03
 */
@ConfigurationProperties(prefix = "iot.jwt")
@Component
public class JwtProperties implements ApplicationRunner {
    private static Logger logger = LoggerFactory.getLogger(JwtProperties.class);
    @Autowired
    private RedisUtils redisUtils;
    /**
     * 密钥
     */
    private String secret;
    /**
     * 过期时间
     */
    private long expire;
    /**
     * 超时时间
     */
    private long sessionTimeout;
    /**
     * 私钥
     */
    private PrivateKey privateKey;
    /**
     * 公钥
     */
    private PublicKey publicKey;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public long getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean key = true;
        while (key){
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                SecureRandom secureRandom = new SecureRandom(secret.getBytes());
                keyPairGenerator.initialize(1024, secureRandom);
                KeyPair keyPair = keyPairGenerator.genKeyPair();
                // 获取公钥并写入REDIS
                byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
                redisUtils.setIfAbsent(Constants.REDIS_USER_PUB_KEY, RsaKeyUtils.toHexString(publicKeyBytes));
                // 获取私钥并写入REDIS
                byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
                redisUtils.setIfAbsent(Constants.REDIS_USER_PRI_KEY, AECUtil.encrypt(RsaKeyUtils.toHexString(privateKeyBytes)));
                // 获取公钥和私钥
                this.publicKey = generatePublicKey();
                this.privateKey = generatePrivateKey();
                logger.info("=======成功初始化公钥和私钥！========");
                key = false;
            } catch (Exception e) {
                logger.error("初始化公钥和私钥失败！", e);
                logger.info("=======重新初始化公钥和私钥！========");
                Thread.sleep(30*1000);
//                移除抛异常代码
//                throw new RuntimeException();
            }
        }
    }

    /**
     * 获取公钥
     *
     * @return
     * @throws Exception
     */
    public  PublicKey generatePublicKey() throws Exception {
        String key = redisUtils.get(Constants.REDIS_USER_PUB_KEY).toString();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(RsaKeyUtils.toBytes(key));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    /**
     * 获取密钥
     *
     * @return
     * @throws Exception
     */
    public  PrivateKey generatePrivateKey() throws Exception {
        String key = redisUtils.get(Constants.REDIS_USER_PRI_KEY).toString();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(RsaKeyUtils.toBytes(AECUtil.decrypt(key)));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }
}
