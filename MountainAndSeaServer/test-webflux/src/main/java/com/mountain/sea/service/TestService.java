package com.mountain.sea.service;

import org.springframework.stereotype.Service;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/7/6 14:28
 */
@Service
public class TestService {

    public String getData(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world";
    }
}
