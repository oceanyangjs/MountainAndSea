package com.mountain.sea.core.utils;

import com.mountain.sea.core.response.Result;
import com.mountain.sea.core.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/24 9:23
 */
public final class WebUtils {
    private WebUtils() {
    }

    public static void write(HttpServletResponse response, Result result) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        Writer writer = response.getWriter();
        writer.write(JsonUtils.serialize(result));
        writer.flush();
        writer.close();
    }
}
