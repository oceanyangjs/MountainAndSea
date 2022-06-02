package com.mountain.sea.security;

import com.mountain.sea.core.response.Result;
import com.mountain.sea.core.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 登陆成功后处理逻辑
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-02 14:15
 */
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        todo 处理redis存储等内容，存储生成token及返回给前端token
        WebUtils.write(response, Result.builder().success(true).body(new HashMap<>()).build());
    }
}
