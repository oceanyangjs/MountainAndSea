package com.mountain.sea.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/24 9:12
 * 此处必须关闭@Lazy,否则会出现获取不到的问题
 */
@Component
@Lazy(false)
public final class ApplicationContextUtils implements ApplicationContextAware {
    private static ApplicationContext context;

    private ApplicationContextUtils() {
    }

    public static <T> T getBean(String beanName) {
        try {
            return (T) context.getBean(beanName);
        } catch (NoSuchBeanDefinitionException var2) {
            return null;
        }
    }

    public static <T> T getBean(Class<T> cls) {
        try {
            return context.getBean(cls);
        } catch (NoSuchBeanDefinitionException var2) {
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    public static void setContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }
}