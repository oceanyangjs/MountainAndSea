package com.mountain.sea.core.context.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * feign请求拦截器,用于进行请求信息过滤等
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/24 10:04
 */
@Component
public class FeignClientInterceptor implements RequestInterceptor {
    public FeignClientInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
//        requestTemplate.header("AUTH_USER_ID", new String[]{request.getHeader("AUTH_USER_ID")});
//        requestTemplate.header("AUTH_USER_NAME", new String[]{request.getHeader("AUTH_USER_NAME")});
//        requestTemplate.header("AUTH_TENANT_ID", new String[]{request.getHeader("AUTH_TENANT_ID")});
//        requestTemplate.header("AUTH_TENANT_NAME", new String[]{request.getHeader("AUTH_TENANT_NAME")});
//        requestTemplate.header("Authorization", new String[]{request.getHeader("Authorization")});
    }
}
