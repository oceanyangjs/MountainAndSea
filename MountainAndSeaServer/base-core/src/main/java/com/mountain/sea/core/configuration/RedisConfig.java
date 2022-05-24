package com.mountain.sea.core.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 14:53
 */
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.dbSize}")
    private int dbSize;
    @Value("${spring.redis.password}")
    private String password;
    private int defaultDB;
    public static Map<Integer, RedisTemplate> redisTemplateMap = new HashMap();

    public RedisConfig() {
    }

    @PostConstruct
    public void initRedisTemplate() {
        this.defaultDB = 0;

        for(int i = 0; i < this.dbSize; ++i) {
            redisTemplateMap.put(i, this.redisTemplate(i));
        }

    }

    public LettuceConnectionFactory redisConnection(int db) {
        RedisStandaloneConfiguration server = new RedisStandaloneConfiguration();
        server.setHostName(this.host);
        server.setDatabase(db);
        if (!StringUtils.isEmpty(this.password)) {
            server.setPassword(this.password);
        }

        server.setPort(this.port);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(server);
        factory.afterPropertiesSet();
        return factory;
    }

    public RedisTemplate redisTemplate(int db) {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(this.redisConnection(db));
        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        jacksonSerializer.setObjectMapper(om);
        template.setValueSerializer(jacksonSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSerializer);
        template.afterPropertiesSet();
        template.afterPropertiesSet();
        return template;
    }

    public RedisTemplate getRedisTemplateByDb(int db) {
        return (RedisTemplate)redisTemplateMap.get(db);
    }

    public RedisTemplate getRedisTemplate() {
        return (RedisTemplate)redisTemplateMap.get(this.defaultDB);
    }
}
