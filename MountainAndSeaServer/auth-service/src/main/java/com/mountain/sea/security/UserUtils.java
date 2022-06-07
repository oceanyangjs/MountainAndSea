package com.mountain.sea.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-07 09:57
 */
public class UserUtils {
    private UserUtils() {

    }

    public static UserDetail getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object user = authentication.getPrincipal();
        if (user != null) {
            return (UserDetail) user;
        }
        return null;
    }

    public static Long getUserId() {
        UserDetail user = getUser();
        return user != null ? user.getId() : null;
    }

    public static String getUsername() {
        UserDetail user = getUser();
        return user != null ? user.getUsername() : null;
    }
}
