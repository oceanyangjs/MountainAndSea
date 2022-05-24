package com.mountain.sea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com"})
public class TestCodeApp
{
    public static void main( String[] args )
    {
        SpringApplication.run(TestCodeApp.class,args);
        System.out.println( "Hello World!" );
    }
}
