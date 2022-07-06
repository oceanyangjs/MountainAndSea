package com.mountain.sea.controller;

import com.mountain.sea.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/7/6 14:26
 */
@RestController
public class TestController {
    @Autowired
    TestService testService;

    @GetMapping("/webflux/common")
    public Object commonReq(){
        long start = System.currentTimeMillis();
        String data = testService.getData();
        System.out.println(System.currentTimeMillis() - start);
        return data;
    }

    @GetMapping("/webflux/demo")
    public Object webfluxReq(){
        long start = System.currentTimeMillis();
        Mono<String> data = Mono.fromSupplier(() -> testService.getData());
        System.out.println(System.currentTimeMillis() - start);
        return data;
    }

    @GetMapping(value = "/webflux/flux",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Object flux(){
        Flux<String> flux = Flux.fromArray(new String[]{"aa","bb","cc"}).map(s -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "mydata is :" + s;
        });
        return flux;
    }
}
