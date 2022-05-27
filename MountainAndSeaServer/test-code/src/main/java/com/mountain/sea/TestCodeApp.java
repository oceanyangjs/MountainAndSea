package com.mountain.sea;

import com.mountain.sea.core.context.feign.EnableFeignTargeter;
import com.mountain.sea.core.context.feign.EnableSentinelFeignRewrite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableSentinelFeignRewrite
@EnableDiscoveryClient
public class TestCodeApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(TestCodeApp.class,args);
        System.out.println( "Hello World!" );
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
