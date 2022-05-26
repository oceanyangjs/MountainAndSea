package com.mountain.sea.core.context.feign;

import com.mountain.sea.core.configuration.FeignConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/25 16:54
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({FeignConfig.class})
public @interface EnableFeignTargeter {
}
