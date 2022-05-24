package com.mountain.sea.core.interceptor;

import com.mountain.sea.core.context.BaseContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 17:12
 */
@Component
public class BaseContextInterceptor implements AsyncHandlerInterceptor, EnvironmentAware {
    private static final String LOCAL_ENV = "local";
    private static final String PROFILE_ACTIVE_KEY = "spring.profiles.active";
    private Environment environment;

    public BaseContextInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String profile = this.environment.getProperty("spring.profiles.active");
        if ("local".equals(profile)) {
            BaseContext.setUserId("1");
            BaseContext.setUserName("superadmin");
            BaseContext.setTenantId("1");
            BaseContext.setTenantName("crrcdter12");
            return true;
        } else {
            String userId = request.getHeader("AUTH_USER_ID");
            String userName = request.getHeader("AUTH_USER_NAME");
            String accessToken = request.getHeader("Authorization");
            String tenantId = request.getHeader("AUTH_TENANT_ID");
            String tenantName = request.getHeader("AUTH_TENANT_NAME");
            BaseContext.setUserId(userId);
            BaseContext.setTenantId(tenantId);
            BaseContext.setUserName(userName);
            BaseContext.setTenantName(tenantName);
            BaseContext.setAccessToken(accessToken);
            return true;
        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
