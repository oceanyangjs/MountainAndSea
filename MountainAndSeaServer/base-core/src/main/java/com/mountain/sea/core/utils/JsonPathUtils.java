package com.mountain.sea.core.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import java.io.IOException;
import java.util.List;

import com.mountain.sea.core.exception.JsonParseException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/8 19:55
 */
public class JsonPathUtils {
    private static Configuration conf;

    private JsonPathUtils() {
    }

    public static String parseString(String json, String jsonPath) throws JsonParseException {
        return (String)parseOject(json, jsonPath, String.class);
    }

    public static int parseInt(String json, String jsonPath) throws JsonParseException {
        Integer obj = (Integer)parseOject(json, jsonPath, Integer.class);
        checkObjectNull(obj, jsonPath);
        return obj;
    }

    public static double parseDouble(String json, String jsonPath) throws JsonParseException {
        Double obj = (Double)parseOject(json, jsonPath, Double.class);
        checkObjectNull(obj, jsonPath);
        return obj;
    }

    public static long parseLong(String json, String jsonPath) throws JsonParseException {
        Long obj = (Long)parseOject(json, jsonPath, Long.class);
        checkObjectNull(obj, jsonPath);
        return obj;
    }

    public static <T> List<T> parseList(String json, String jsonPath, Class<T> clazz) throws IOException, JsonParseException {
        check(json, jsonPath);
        String sourceJson = JsonPath.read(json, jsonPath, new Predicate[0]).toString();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return (List)mapper.readValue(sourceJson, javaType);
    }

    public static <T> T parseOject(String json, String jsonPath, Class<T> cls) throws JsonParseException {
        check(json, jsonPath);
        return JsonPath.using(conf).parse(json).read(jsonPath, cls, new Predicate[0]);
    }

    private static void check(String json, String jsonPath) throws JsonParseException {
        if (org.apache.commons.lang3.StringUtils.isEmpty(json)) {
            throw new JsonParseException("Json字符串不能为空");
        } else if (StringUtils.isEmpty(jsonPath)) {
            throw new JsonParseException("Json path不能为空");
        }
    }

    private static void checkObjectNull(Object obj, String jsonPath) throws JsonParseException {
        if (obj == null) {
            throw new JsonParseException("Json path[" + jsonPath + "]不存在");
        }
    }

    static {
        conf = Configuration.builder().jsonProvider(new JacksonJsonProvider()).mappingProvider(new JacksonMappingProvider()).options(new Option[]{Option.DEFAULT_PATH_LEAF_TO_NULL}).build();
    }
}
