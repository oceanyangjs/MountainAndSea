package com.mountain.sea.core.context.feign;

import java.lang.reflect.Method;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/25 9:28
 */
public class FeignFallbackHandler extends BaseFallbackHandler {
    public FeignFallbackHandler(Class<?> feignClientClass, Throwable cause) {
        super(feignClientClass, cause);
    }

    @Override
    protected Object handle(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        return String.class.equals(returnType) ? FallbackResults.defaultFallbackStringResult() : FallbackResults.defaultFallbackResult();
    }
}
