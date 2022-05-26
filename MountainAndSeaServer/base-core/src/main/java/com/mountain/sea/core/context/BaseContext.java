package com.mountain.sea.core.context;

import com.mountain.sea.core.exception.RestApiException;
import com.mountain.sea.core.exception.ExceptionEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/5/23 17:12
 */
public class BaseContext {
    public static final ThreadLocal<Map<String, Object>> context = new ThreadLocal();

    public BaseContext() {
    }

    public static void set(String key, Object value) {
        Map<String, Object> valueMap = (Map)context.get();
        if (valueMap == null) {
            valueMap = new HashMap();
            context.set(valueMap);
        }

        ((Map)valueMap).put(key, value);
    }

    public static Object get(String key) {
        Map<String, Object> valueMap = (Map)context.get();
        return valueMap == null ? null : valueMap.get(key);
    }

    public static Long getUserId() {
        String userId = (String)get("userId");
        if (StringUtils.isEmpty(userId)) {
            throw new RestApiException("9999", "用户身份异常");
        } else {
            return Long.parseLong(userId);
        }
    }

    public static void setUserId(Object userId) {
        set("userId", userId);
    }

    public static void setTenantId(Object tenantId) {
        set("tenantId", tenantId);
    }

    public static Long getTenantId() {
        String tenantId = (String)get("tenantId");
        if (StringUtils.isEmpty(tenantId)) {
            throw new RestApiException(ExceptionEnum.USER_IDENTITY_EXCEPTION);
        } else {
            return Long.parseLong(tenantId);
        }
    }

    public static void setTenantName(Object tenantName) {
        set("tenantName", tenantName);
    }

    public static String getTenantName() {
        String tenantName = (String)get("tenantName");
        if (StringUtils.isEmpty(tenantName)) {
            throw new RestApiException(ExceptionEnum.USER_IDENTITY_EXCEPTION);
        } else {
            return tenantName;
        }
    }

    public static String getUserName() {
        String username = (String)get("userName");
        if (StringUtils.isEmpty(username)) {
            throw new RestApiException(ExceptionEnum.USER_IDENTITY_EXCEPTION);
        } else {
            return username;
        }
    }

    public static void setUserName(String userName) {
        set("userName", userName);
    }

    public static String getAccessToken() {
        String accessToken = (String)get("accessToken");
        if (StringUtils.isEmpty(accessToken)) {
            throw new RestApiException(ExceptionEnum.USER_IDENTITY_EXCEPTION);
        } else {
            return accessToken;
        }
    }

    public static void setAccessToken(String accessToken) {
        set("accessToken", accessToken);
    }
}
