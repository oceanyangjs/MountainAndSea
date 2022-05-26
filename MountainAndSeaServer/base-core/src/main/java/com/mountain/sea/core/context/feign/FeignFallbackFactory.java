package com.mountain.sea.core.context.feign;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * feign 异常统一处理
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/24 15:05
 */
public class FeignFallbackFactory implements FallbackFactory<FallbackEnableFeignClient> {
    private Class<?> feignClientClass;
    private String random;

    public FeignFallbackFactory() {
        System.out.println("init");
        random = String.valueOf(Math.random() + System.currentTimeMillis());
    }

    @Override
    public FallbackEnableFeignClient create(Throwable cause) {
        return this.doCreate(cause);
    }

    protected FallbackEnableFeignClient doCreate(Throwable cause) {
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(Thread.currentThread().getContextClassLoader());
        enhancer.setSuperclass(this.getFeignClientClass());
        enhancer.setCallback(new FeignFallbackHandler(this.feignClientClass,cause));
        return (FallbackEnableFeignClient)enhancer.create();
    }

    public Class<?> getFeignClientClass() {
        return this.feignClientClass;
    }

    public void setFeignClientClass(Class<?> feignClientClass) {
        this.feignClientClass = feignClientClass;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }
}
