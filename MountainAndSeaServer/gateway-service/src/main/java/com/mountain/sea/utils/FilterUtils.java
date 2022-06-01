package com.mountain.sea.utils;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-06-01 14:06
 */
public class FilterUtils {
    public static ServerHttpRequest getHttpRequest(ServerHttpRequest httpRequest
            , Consumer<HttpHeaders> httpHeadersConsumer, Flux<DataBuffer> dataBufferFlux) {
        ServerHttpRequest newHttpRequest = httpRequest.mutate()
                .headers(httpHeadersConsumer)
                .build();
        return new ServerHttpRequestDecorator(newHttpRequest) {
            @Override
            public Flux<DataBuffer> getBody() {
                return dataBufferFlux;
            }
        };
    }

    public static ServerHttpRequest getHttpRequest(ServerHttpRequest httpRequest
            , Consumer<HttpHeaders> httpHeadersConsumer) {
        return  httpRequest.mutate()
                .headers(httpHeadersConsumer)
                .build();
    }

    public static Consumer<HttpHeaders> getHttpHeadersConsumer(Map<String, Object> userInfo, String auth_token) {
        Consumer<HttpHeaders> consumer = headers -> {
//            headers.set(Constants.AUTH_USER_ID_HEADER_KEY,String.valueOf(userInfo.get(Constants.CONTEXT_USER_ID)));
//            headers.set(Constants.AUTH_USER_NAME_HEADER_KEY,(String)userInfo.get(Constants.CONTEXT_USER_NAME));
//            headers.set(Constants.AUTH_USER_TENANT_HEADER_KEY,String.valueOf(userInfo.get(Constants.CONTEXT_TENANT_ID)));
//            headers.set(Constants.AUTH_USER_TENANT_NAME_HEADER_KEY,(String)userInfo.get(Constants.CONTEXT_TENANT_NAME));
//            headers.set(Constants.HTTP_HEADER_TOKEN_NAME,auth_token);
        };
        return consumer;
    }

    public static ServerWebExchange getServerWebExchange(ServerWebExchange exchange, ServerHttpRequest request){
        return  exchange.mutate().request(request).build();
    }
}
