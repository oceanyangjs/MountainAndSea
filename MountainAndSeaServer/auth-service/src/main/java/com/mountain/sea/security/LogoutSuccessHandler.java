package com.mountain.sea.security;

import com.mountain.sea.core.response.Result;
import com.mountain.sea.core.utils.WebUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-02 14:37
 */
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        WebUtils.write(response, Result.builder().success(true).build());
    }
}
