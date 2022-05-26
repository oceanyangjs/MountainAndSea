package com.mountain.sea;

import com.mountain.sea.core.context.feign.EnableFeignTargeter;
import com.mountain.sea.core.context.feign.EnableSentinelFeignRewrite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableSentinelFeignRewrite
public class TestCodeApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(TestCodeApp.class,args);
        System.out.println( "Hello World!" );
    }
}
