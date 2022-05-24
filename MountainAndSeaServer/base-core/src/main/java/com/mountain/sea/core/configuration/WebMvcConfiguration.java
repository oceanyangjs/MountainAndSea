package com.mountain.sea.core.configuration;

import com.mountain.sea.core.interceptor.BaseContextInterceptor;
import com.mountain.sea.core.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 17:05
 */
public class WebMvcConfiguration implements WebMvcConfigurer {
    private static final String VALIDATION_FAIL_FAST = "true";
    @Autowired
    private BaseContextInterceptor baseContextInterceptor;
    @Autowired
    private PermissionInterceptor permissionInterceptor;

    public WebMvcConfiguration() {
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.baseContextInterceptor).addPathPatterns(new String[]{"/**"});
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"swagger-ui.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
    }

//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//    @Override
//    public Validator getValidator() {
//        return this.validator();
//    }
//
//    @Bean
//    public MethodValidationPostProcessor methodValidationPostProcessor() {
//        MethodValidationPostProcessor postProcessor = new MethodValidationPostProcessor();
//        postProcessor.setValidator(this.validator());
//        return postProcessor;
//    }
//
//    @Bean
//    public LocalValidatorFactoryBean validator() {
//        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
//        validator.setProviderClass(HibernateValidator.class);
//        Map<String, String> config = new HashMap();
//        config.put("hibernate.validator.fail_fast", "true");
//        validator.setValidationPropertyMap(config);
//        validator.afterPropertiesSet();
//        return validator;
//    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template;
    }
}
