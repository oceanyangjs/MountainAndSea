package com.mountain.sea.utils;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/14 13:23
 */
public class PropertiesFileUtil {
    public static Properties getProperties(String file) throws Exception {
        Properties properties = new Properties();
        FileInputStream inputStream = new FileInputStream(file);
        properties.load(inputStream);
        return properties;
    }
}
