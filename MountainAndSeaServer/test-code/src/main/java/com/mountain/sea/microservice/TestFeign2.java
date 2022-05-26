package com.mountain.sea.microservice;

import com.mountain.sea.core.context.feign.FallbackEnableFeignClient;
import com.mountain.sea.core.context.feign.FeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/25 16:05
 */
@FeignClient(name = "test-feign2-service",url = "http://127.0.0.1",fallbackFactory = FeignFallbackFactory.class)
public interface TestFeign2 extends FallbackEnableFeignClient {

    @PostMapping(value = "/test")
    String test();
}
