package com.mountain.sea.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/20 16:37
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public Object test(){
        return "hello world";
    }
}
