package com.mountain.sea.core.configuration;

import com.mountain.sea.core.interceptor.SqlInterceptor;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 15:49
 */
public class MybatisPluginConfiguration {
    @Value("${mybatis.dialect}")
    private String helperDialect;
    @Value("${mybatis.enum-locations}")
    private String enumLocations;

    public MybatisPluginConfiguration() {
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return (configuration) -> {
            configuration.setMapUnderscoreToCamelCase(true);
            //            pageHelper插件配置
            PageInterceptor pageInterceptor = new PageInterceptor();
            Properties p = new Properties();
            p.getProperty("helperDialect", this.helperDialect);
            pageInterceptor.setProperties(p);
            configuration.addInterceptor(pageInterceptor);
            //          sql拦截器插件配置
            configuration.addInterceptor(new SqlInterceptor());
            //            枚举类型处理插件配置
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
//          todo 以下代码对于mac系统存在问题，获取类时路径的截取异常，在windows系统中操作正常。故暂时注释
//            try {
//                List<Class<?>> allAssignedClass = ClassUtils.getAllAssignedClass(BaseEnum.class, this.enumLocations);
//                allAssignedClass.forEach((clazz) -> {
//                    typeHandlerRegistry.register(clazz, GeneralTypeHandler.class);
//                });
//            } catch (ClassNotFoundException var6) {
//                var6.printStackTrace();
//            }

        };
    }
}
