package com.mountain.sea.core.context.feign;

import com.mountain.sea.core.configuration.FeignConfig;
import com.mountain.sea.core.configuration.FeignSentinelConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 重写sentinelFeign,利用sentinel熔断并对feign异常进行统一处理
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/26 13:59
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FeignSentinelConfig.class})
public @interface EnableSentinelFeignRewrite {
}
