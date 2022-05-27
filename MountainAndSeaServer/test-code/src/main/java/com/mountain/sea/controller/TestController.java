package com.mountain.sea.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.mountain.sea.microservice.TestFeign;
import com.mountain.sea.microservice.TestFeign2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/20 16:37
 */
@RestController
@RefreshScope
public class TestController {
    @Autowired
    TestFeign testFeign;
    @Autowired
    TestFeign2 testFeign2;
    @Value("${nacos.test.data:abc}")
    String testData;

    @RequestMapping("/test")
    @SentinelResource("/test")
    public Object a(){
//        throw new RestApiException("100","test");
        System.out.println(testData);
        return "hello world";
    }

    @RequestMapping("/testFeign")
    @SentinelResource("/testFeign")
    public Object testFeign(){
        return testFeign.test();
    }

    @RequestMapping("/testFeign2")
    @SentinelResource("/testFeign2")
    public Object testFeign2(){
        return testFeign2.test();
    }
}
