package com.mountain.sea.core.configuration;

import com.mountain.sea.core.context.feign.FeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignFallbackConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/25 16:30
 */
//@Configuration
//@Import({FeignFallbackConfiguration.class})
public class FeignConfig {

    /**
     * 由于当多个应用实例时需要生成多个factory类,各类之间需要隔离,因此采用prototype
     * @return
     */
    @Bean
    @Scope("prototype")
    public FeignFallbackFactory feignFallbackFactory() {
//        FeignFallbackFactory feignFallbackFactory = new FeignFallbackFactory();
        return new FeignFallbackFactory();
    }
}
