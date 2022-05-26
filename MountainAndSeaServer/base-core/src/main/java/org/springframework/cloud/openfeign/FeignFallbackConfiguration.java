package org.springframework.cloud.openfeign;

import feign.Feign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/25 14:39
 */
@Configuration
@ConditionalOnClass({Feign.class})
public class FeignFallbackConfiguration {
    public FeignFallbackConfiguration() {
    }

    @Configuration
//    @ConditionalOnClass(
//            name = {"sentinel.feign.SentinelFeign"}
//    )
    protected static class HystrixFeignTargeterConfiguration {
        protected HystrixFeignTargeterConfiguration() {
        }

//        @Bean
//        @ConditionalOnMissingBean
//        public Targeter feignTargeter() {
////            return new FeignFallbackTargeter();
//            return new FeignCircuitTargeter();
//        }

        @Bean
        @ConditionalOnMissingBean
//        @ConditionalOnBean(CircuitBreakerFactory.class)
        public Targeter circuitBreakerFeignTargeter(CircuitBreakerFactory circuitBreakerFactory,
                                                    @Value("${feign.circuitbreaker.group.enabled:false}") boolean circuitBreakerGroupEnabled,
                                                    CircuitBreakerNameResolver circuitBreakerNameResolver) {
            return new FeignCircuitTargeter(circuitBreakerFactory, circuitBreakerGroupEnabled,
                    circuitBreakerNameResolver);
        }
    }
}
