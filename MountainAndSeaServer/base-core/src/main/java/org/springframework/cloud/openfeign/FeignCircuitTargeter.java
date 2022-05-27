package org.springframework.cloud.openfeign;

import com.alibaba.cloud.sentinel.feign.SentinelFeign;
import com.mountain.sea.core.context.feign.FeignFallbackFactory;
import feign.Feign;
import feign.Target;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.util.StringUtils;

/**
 * @deprecated
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/26 9:34
 */
public class FeignCircuitTargeter implements Targeter{
    private final CircuitBreakerFactory circuitBreakerFactory;

    private final boolean circuitBreakerGroupEnabled;

    private final CircuitBreakerNameResolver circuitBreakerNameResolver;

    FeignCircuitTargeter(CircuitBreakerFactory circuitBreakerFactory, boolean circuitBreakerGroupEnabled,
                                CircuitBreakerNameResolver circuitBreakerNameResolver) {
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.circuitBreakerGroupEnabled = circuitBreakerGroupEnabled;
        this.circuitBreakerNameResolver = circuitBreakerNameResolver;
    }

    @Override
    public <T> T target(FeignClientFactoryBean factory, Feign.Builder feign, FeignContext context,
                        Target.HardCodedTarget<T> target) {
        if (!(feign instanceof FeignCircuitBreaker.Builder) && !(feign instanceof SentinelFeign.Builder)) {
            return feign.target(target);
        }
//        处理sentinel熔断
        if(feign instanceof SentinelFeign.Builder){
//            T target1 = feign.target(target);
            String feignClientName = !StringUtils.hasText(factory.getContextId()) ? factory.getName() : factory.getContextId();
            Class<?> fallbackFactoryClass = factory.getFallbackFactory();
            FallbackFactory<? extends T> fallbackFactory = (FallbackFactory<? extends T>) getFromContext("fallbackFactory",
                    feignClientName, context, fallbackFactoryClass, FallbackFactory.class);
            if (fallbackFactory instanceof FeignFallbackFactory) {
//            FeignFallbackFactory feignFallbackFactory = (FeignFallbackFactory)fallbackFactory;
                ((FeignFallbackFactory)fallbackFactory).setFeignClientClass(target.type());
            }
            return feign.target(target);
//            String name = !StringUtils.hasText(factory.getContextId()) ? factory.getName() : factory.getContextId();
//            FeignCircuitBreaker.Builder builder = (FeignCircuitBreaker.Builder) feign;
//            feign.
//            feign.target()
//            return builder(name, builder).target(target);
//            return target1;
//            return feign.target(target);
        }
        FeignCircuitBreaker.Builder builder = (FeignCircuitBreaker.Builder) feign;
        String name = !StringUtils.hasText(factory.getContextId()) ? factory.getName() : factory.getContextId();
        Class<?> fallback = factory.getFallback();
        if (fallback != void.class) {
            return targetWithFallback(name, context, target, builder, fallback);
        }
        Class<?> fallbackFactory = factory.getFallbackFactory();
        if (fallbackFactory != void.class) {
            return targetWithFallbackFactory(name, context, target, builder, fallbackFactory);
        }
        return builder(name, builder).target(target);
    }

    private <T> T targetWithFallbackFactory(String feignClientName, FeignContext context,
                                            Target.HardCodedTarget<T> target, FeignCircuitBreaker.Builder builder, Class<?> fallbackFactoryClass) {
        FallbackFactory<? extends T> fallbackFactory = (FallbackFactory<? extends T>) getFromContext("fallbackFactory",
                feignClientName, context, fallbackFactoryClass, FallbackFactory.class);
        if (fallbackFactory instanceof FeignFallbackFactory) {
//            FeignFallbackFactory feignFallbackFactory = (FeignFallbackFactory)fallbackFactory;
            ((FeignFallbackFactory)fallbackFactory).setFeignClientClass(target.type());
        }

//        return builder.target(target);
        return builder(feignClientName, builder).target(target, fallbackFactory);
    }

    private <T> T targetWithFallback(String feignClientName, FeignContext context, Target.HardCodedTarget<T> target,
                                     FeignCircuitBreaker.Builder builder, Class<?> fallback) {
        T fallbackInstance = getFromContext("fallback", feignClientName, context, fallback, target.type());
        return builder(feignClientName, builder).target(target, fallbackInstance);
    }

    private <T> T getFromContext(String fallbackMechanism, String feignClientName, FeignContext context,
                                 Class<?> beanType, Class<T> targetType) {
        Object fallbackInstance = context.getInstance(feignClientName, beanType);
        if (fallbackInstance == null) {
            throw new IllegalStateException(
                    String.format("No " + fallbackMechanism + " instance of type %s found for feign client %s",
                            beanType, feignClientName));
        }

        if (!targetType.isAssignableFrom(beanType)) {
            throw new IllegalStateException(String.format("Incompatible " + fallbackMechanism
                            + " instance. Fallback/fallbackFactory of type %s is not assignable to %s for feign client %s",
                    beanType, targetType, feignClientName));
        }
        return (T) fallbackInstance;
    }

    private FeignCircuitBreaker.Builder builder(String feignClientName, FeignCircuitBreaker.Builder builder) {
        return builder.circuitBreakerFactory(circuitBreakerFactory).feignClientName(feignClientName)
                .circuitBreakerGroupEnabled(circuitBreakerGroupEnabled)
                .circuitBreakerNameResolver(circuitBreakerNameResolver);
    }
}
