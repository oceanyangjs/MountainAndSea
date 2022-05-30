package com.mountain.sea.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/30 17:23
 */
@RestController
public class TestController {
    @GetMapping("/test")
    public Object test(){
        return "hello";
    }

}
