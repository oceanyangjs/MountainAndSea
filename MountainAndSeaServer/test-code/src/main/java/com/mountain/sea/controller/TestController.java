package com.mountain.sea.controller;

import com.mountain.sea.core.exception.RestApiException;
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
    public Object a(){
        throw new RestApiException("100","test");
//        return "hello world";
    }
}
