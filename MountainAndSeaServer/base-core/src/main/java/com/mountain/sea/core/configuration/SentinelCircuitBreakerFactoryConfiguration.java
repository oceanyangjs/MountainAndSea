package com.mountain.sea.core.configuration;

import com.alibaba.cloud.circuitbreaker.sentinel.SentinelCircuitBreakerFactory;
import com.alibaba.cloud.circuitbreaker.sentinel.SentinelConfigBuilder;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.google.common.base.Function;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * sentinel断路器
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/24 14:33
 */
@Configuration
public class SentinelCircuitBreakerFactoryConfiguration {
    /**
     * 默认断路器设置
     * @return
     */
    @Bean
    public Customizer<SentinelCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new SentinelConfigBuilder(id)
                .build());
    }

    /**
     * 特定断路器设置
     * @return
     */
    @Bean
    public Customizer<SentinelCircuitBreakerFactory> slowCustomizer() {
        String slowId = "slow";
        List<DegradeRule> rules = Collections.singletonList(
                new DegradeRule(slowId).setGrade(RuleConstant.DEGRADE_GRADE_RT)
                        .setCount(100)
                        .setTimeWindow(10)
        );
        return factory -> factory.configure(builder -> builder.rules(rules), slowId);
    }
}
