package com.mountain.sea.core.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 11:18
 */
public class JsonUtils {
    public static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    public JsonUtils() {
    }

    public static String serialize(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj.getClass() == String.class) {
            return (String)obj;
        } else {
            try {
                return mapper.writeValueAsString(obj);
            } catch (JsonProcessingException var2) {
                logger.error("json序列化出错：" + obj, var2);
                return null;
            }
        }
    }

    public static <T> T parse(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException var3) {
            logger.error("json解析出错：" + json, var3);
            return null;
        }
    }

    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return (List)mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException var3) {
            logger.error("json解析出错：" + json, var3);
            return null;
        }
    }

    public static <K, V> Map<K, V> parseMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return (Map)mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException var4) {
            logger.error("json解析出错：" + json, var4);
            return null;
        }
    }

    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException var3) {
            logger.error("json解析出错：" + json, var3);
            return null;
        }
    }
}
