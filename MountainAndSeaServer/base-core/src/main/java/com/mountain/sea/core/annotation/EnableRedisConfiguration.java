package com.mountain.sea.core.annotation;

import com.mountain.sea.core.configuration.RedisConfig;
import com.mountain.sea.core.utils.MultiRedisUtils;
import com.mountain.sea.core.utils.RedisUtils;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 15:14
 * redis引用注解
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({RedisUtils.class, MultiRedisUtils.class, RedisConfig.class})
public @interface EnableRedisConfiguration {
}
