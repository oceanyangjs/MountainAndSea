package com.mountain.sea;

import com.mountain.sea.core.context.feign.EnableSentinelFeignRewrite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableSentinelFeignRewrite
@EnableDiscoveryClient
public class NettyApp
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(NettyApp.class,args);
    }
}
