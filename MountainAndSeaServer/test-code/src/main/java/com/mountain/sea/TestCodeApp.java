package com.mountain.sea;

import com.mountain.sea.core.context.feign.EnableSentinelFeignRewrite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 测试代码启动器
 * @version 1.0
 * @date 2022/5/20 11:37
 * @author Yang Jingsheng
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
