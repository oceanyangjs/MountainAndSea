package com.mountain.sea.core.interceptor;

import com.mountain.sea.core.exception.ExceptionEnum;
import com.mountain.sea.core.exception.RestApiException;
import com.mountain.sea.core.annotation.AccessPermission;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 17:07
 */
@Component
public class PermissionInterceptor implements AsyncHandlerInterceptor {
    public PermissionInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        List<String> permissions = new ArrayList();
        permissions.add("add_user");
        if (method.isAnnotationPresent(AccessPermission.class)) {
            AccessPermission accessPermission = (AccessPermission)method.getAnnotation(AccessPermission.class);
            String[] values = accessPermission.values();
            permissions.retainAll(Arrays.asList(values));
            if (permissions.size() == 0) {
                throw new RestApiException(ExceptionEnum.REQUEST_NOT_ACCESS);
            }
        }

        return true;
    }
}

