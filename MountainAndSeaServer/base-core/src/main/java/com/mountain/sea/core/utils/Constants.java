package com.mountain.sea.core.utils;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-07 10:43
 */
public final class Constants {
    public static final boolean SUCCESS = true;
    public static final boolean FAILURE = false;
    public static final String REDIS_USER_TOKEN_KEY_PREFIX = "USER_TOKEN:";
    public static final String CONTEXT_USER_ID = "userId";
    public static final String CONTEXT_USER_NAME = "userName";
    public static final String CONTEXT_TENANT_ID = "tenantId";
    public static final String CONTEXT_TENANT_NAME = "tenantName";
    public static final String CONTEXT_ACCESS_TOKEN = "accessToken";
    public static final String TOKEN_KEY = "access_token";
    public static final String HTTP_HEADER_TOKEN_NAME = "Authorization";
    public static final String DATASOURCE_USER = "public.datasource.user";
    public static final String DATASOURCE_PASSWORD = "public.datasource.password";
    public static final String DATASOURCE_URL = "public.datasource.url";
    public static final String DATASOURCE_MAXPOOlSIZE = "public.datasource.maximumPoolSize";
    public static final String DATASOURCE_MINIDLE = "public.datasource.minimumIdle";
    public static final String DATASOURCE_DRIVERCLASSNAME = "public.datasource.driverClassName";
    public static final String USER_NAME_NOT_FOUND = "[username not found]";
    public static final String REDIS_USER_PUB_KEY = "IIOT:AUTH:JWT:PUB";
    public static final String REDIS_USER_PRI_KEY = "IIOT:AUTH:JWT:PRI";
    public static final String SESSION_TIMEOUT_MESSAGE = "登录已过期，请重新登录！";
    public static final String SESSION_LOGOUT_MESSAGE = "登录已超时或已退出，请重新登录！";
    public static final String INVALID_AUTH_MESSAGE = "无效认证，请重新登录！";
    public static final String AUTH_VALID_ERROR_MESSAGE = "验证异常";
    public static final String AUTH_USER_ID_HEADER_KEY = "AUTH_USER_ID";
    public static final String AUTH_USER_NAME_HEADER_KEY = "AUTH_USER_NAME";
    public static final String AUTH_USER_TENANT_HEADER_KEY = "AUTH_TENANT_ID";
    public static final String AUTH_USER_TENANT_NAME_HEADER_KEY = "AUTH_TENANT_NAME";
    public static final String UTF8_CHARSET = "UTF-8";
}
