package com.mountain.sea.configuration;

import com.mountain.sea.filter.RequestInfoFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-05-31 16:29
 */
@Configuration
public class GatewayConfiguration {

    @Bean
    public RequestInfoFilter requestInfoFilter(){
        return new RequestInfoFilter();
    }
}
