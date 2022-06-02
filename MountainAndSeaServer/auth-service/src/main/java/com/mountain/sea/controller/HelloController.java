package com.mountain.sea.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-02 10:35
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public Object hello(){
        return "hello spring security";
    }
}
