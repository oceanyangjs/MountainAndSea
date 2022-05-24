package com.mountain.sea.core.annotation;

import com.mountain.sea.core.configuration.MybatisPluginConfiguration;
import com.mountain.sea.core.configuration.SwaggerConfiguration;
import com.mountain.sea.core.configuration.WebMvcConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 17:04
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import({MybatisPluginConfiguration.class, SwaggerConfiguration.class, WebMvcConfiguration.class})
public @interface EnableWebServiceConfoguration {
}
