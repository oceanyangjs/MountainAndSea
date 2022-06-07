package com.mountain.sea.security;

import com.mountain.sea.config.JwtProperties;
import com.mountain.sea.core.response.Result;
import com.mountain.sea.core.utils.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆成功后处理逻辑
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-02 14:15
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private JwtProperties jwtProperties;
    private RedisUtils redisUtils;
    public LoginSuccessHandler(JwtProperties jwtProperties,RedisUtils redisUtils) {
        this.jwtProperties = jwtProperties;
        this.redisUtils = redisUtils;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        todo 处理redis存储等内容，存储生成token及返回给前端token
        String accessToken;
        String userKey = Constants.REDIS_USER_TOKEN_KEY_PREFIX.concat(String.valueOf(UserUtils.getUserId()));
        accessToken = (String) redisUtils.get(userKey);
        if(!StringUtils.isEmpty(accessToken) && !JwtUtils.isTokenExpired(accessToken,jwtProperties.getPublicKey())){
            redisUtils.expire(userKey, jwtProperties.getSessionTimeout());
        }else{
            Map<String,Object> claims = new HashMap<>();
            claims.put(Constants.CONTEXT_USER_ID,UserUtils.getUserId());
            claims.put(Constants.CONTEXT_USER_NAME,UserUtils.getUsername());
            accessToken = JwtUtils.generateToken(UserUtils.getUsername(),claims,jwtProperties.getPrivateKey(),jwtProperties.getExpire());
            redisUtils.set(userKey,accessToken, jwtProperties.getSessionTimeout());
        }
        Map<String,String> result = new HashMap<>();
        result.put(Constants.TOKEN_KEY,accessToken);
        WebUtils.write(response, Result.builder().success(true).body(result).build());
    }
}
