package com.mountain.sea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 网关启动器
 * @version 1.0
 * @date 2022/5/20 11:37
 * @author Yang Jingsheng
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(GatewayApp.class,args);
        System.out.println( "Hello World!" );
    }
}
