package com.mountain.sea.core.context.feign;

import com.mountain.sea.core.response.Result;
import com.mountain.sea.core.utils.JsonUtils;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/25 9:55
 */
public class FallbackResults {
    public FallbackResults() {
    }

    public static Result defaultFallbackResult() {
        return Result.builder().success(false).error("400", "服务异常").build();
    }

    public static String defaultFallbackStringResult() {
        return JsonUtils.serialize(Result.builder().success(false).error("400", "服务异常").build());
    }
}
