package com.mountain.sea.security;

import com.mountain.sea.core.exception.ExceptionEnum;
import com.mountain.sea.core.response.Result;
import com.mountain.sea.core.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-02 15:07
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        WebUtils.write(response,
                Result.builder().success(false).error(ExceptionEnum.AUTHENTICATION_ENTRYPOINT_EXCEPTION).build());
    }
}
