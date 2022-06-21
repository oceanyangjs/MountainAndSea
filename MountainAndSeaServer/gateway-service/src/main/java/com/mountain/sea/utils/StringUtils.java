package com.mountain.sea.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-05-31 16:23
 */
public class StringUtils {
    public StringUtils() {
    }

    public static boolean isEmpty(String source) {
        return source == null || source.isEmpty();
    }

    public static boolean isTrimEmpty(String source) {
        return source == null || source.trim().isEmpty();
    }

    public static boolean isNotEmpty(String source) {
        return !isEmpty(source);
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            format.setLenient(false);
            format.parse(str);
        } catch (NullPointerException | ParseException var4) {
            convertSuccess = false;
        }

        return convertSuccess;
    }

    public static String list2String(List<String> list) {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        for(int i = 0; i < list.size(); ++i) {
            sb.append('"').append((String)list.get(i)).append('"');
            if (i != list.size() - 1) {
                sb.append(',');
            }
        }

        sb.append(']');
        return sb.toString();
    }
}
