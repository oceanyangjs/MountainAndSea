package com.mountain.sea.core.context.feign;

import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/25 9:46
 */
public abstract class BaseFallbackHandler implements InvocationHandler {
    private static final List<String> JAVA_OBJECT_METHODS = Arrays.asList("equals", "hashCode", "toString", "clone", "finalize", "getClass", "notify", "notifyAll", "wait");
    private final Class<?> feignClientClass;
    private final Throwable cause;

    public BaseFallbackHandler(Class<?> feignClientClass, Throwable cause) {
        this.feignClientClass = feignClientClass;
        this.cause = cause;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String feignClientClassName = this.feignClientClass.getName();
        String methodName = method.getName();
        String methodExpression = method.toGenericString();
        Class<?>[] methodParams = method.getParameterTypes();
        if (JAVA_OBJECT_METHODS.contains(methodName)) {
            if ("equals".equals(methodName) && methodParams.length == 1) {
                return false;
            }

            if ("hashCode".equals(methodName) && methodParams.length == 0) {
                return (feignClientClassName + "." + methodExpression).hashCode();
            }

            if ("toString".equals(methodName) && methodParams.length == 0) {
                return "HystrixFallbackProxy[" + feignClientClassName + "." + methodExpression + "]";
            }

            if ("clone".equals(methodName) && methodParams.length == 0) {
                return null;
            }

            if ("finalize".equals(methodName) && methodParams.length == 0) {
                throw new UnsupportedOperationException("Operation Not Supported!");
            }

            if ("getClass".equals(methodName) && methodParams.length == 0) {
                return this.feignClientClass;
            }

            if ("notify".equals(methodName) && methodParams.length == 0) {
                throw new UnsupportedOperationException("Operation Not Supported!");
            }

            if ("notifyAll".equals(methodName) && methodParams.length == 0) {
                throw new UnsupportedOperationException("Operation Not Supported!");
            }

            if ("wait".equals(methodName)) {
                throw new UnsupportedOperationException("Operation Not Supported!");
            }
        }

        return this.handle(proxy, method, args);
    }

    /**
     * 实际处理逻辑
     * @param var1
     * @param var2
     * @param var3
     * @return
     * @throws Throwable
     */
    protected abstract Object handle(Object var1, Method var2, Object[] var3) throws Throwable;

    public Class<?> getFeignClientClass() {
        return this.feignClientClass;
    }

    public Throwable getCause() {
        return this.cause;
    }
}
