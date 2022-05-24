package com.mountain.sea.core.annotation;

import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 17:10
 */
@Documented
@ResponseBody
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AccessPermission {
    String[] values() default {};
}
