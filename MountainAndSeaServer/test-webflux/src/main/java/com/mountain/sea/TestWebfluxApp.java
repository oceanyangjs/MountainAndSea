package com.mountain.sea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * webflux服务demo
 *
 */
@SpringBootApplication
@EnableFeignClients
//@EnableSentinelFeignRewrite
@EnableDiscoveryClient
public class TestWebfluxApp
{
    public static void main( String[] args )
    {
        System.out.println( "Hello webflux!" );
        SpringApplication.run(TestWebfluxApp.class,args);
    }
}
