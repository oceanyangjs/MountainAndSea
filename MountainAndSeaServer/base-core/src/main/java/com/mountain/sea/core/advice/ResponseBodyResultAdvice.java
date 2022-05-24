package com.mountain.sea.core.advice;

import com.mountain.sea.core.annotation.ResponseBodyResult;
import com.mountain.sea.core.response.Result;
import com.mountain.sea.core.utils.JsonUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/20 16:09
 */
@RestControllerAdvice
public class ResponseBodyResultAdvice implements ResponseBodyAdvice<Object> {
    public ResponseBodyResultAdvice() {
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), ResponseBodyResult.class) || returnType.hasMethodAnnotation(ResponseBodyResult.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            return body;
        } else {
            Result result = Result.builder().success(true).body(body).build();
            return body instanceof String ? JsonUtils.serialize(result) : result;
        }
    }
}
