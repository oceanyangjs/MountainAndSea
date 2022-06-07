package com.mountain.sea;

import com.mountain.sea.core.annotation.EnableRedisConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 认证服务启动器
 * @version 1.0
 * @date 2022/5/20 11:37
 * @author Yang Jingsheng
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableRedisConfiguration
public class AuthApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(AuthApp.class,args);
        System.out.println( "Hello World!" );
    }
}
