package com.mountain.sea.security;

import com.mountain.sea.core.exception.ExceptionEnum;
import com.mountain.sea.core.response.Result;
import com.mountain.sea.core.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆失败后逻辑
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-02 14:18
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        WebUtils.write(response,
                Result.builder().success(false).error(ExceptionEnum.AUTHENTICATION_EXCEPTION).build());
    }
}
