package com.mountain.sea.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-02 14:40
 */
public class LogoutOptionHandler implements LogoutHandler {
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
//        todo 进行token信息清理
    }
}
