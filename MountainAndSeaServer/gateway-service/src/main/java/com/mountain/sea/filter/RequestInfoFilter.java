package com.mountain.sea.filter;

import com.mountain.sea.utils.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-05-31 16:18
 */
public class RequestInfoFilter implements GlobalFilter {
    private static Logger logger = LoggerFactory.getLogger(RequestInfoFilter.class);
    private String requestHeadClientIp = "request_header_client_ip";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = IpUtils.getRealIpAddress(exchange.getRequest());
        // 该步骤可选。可以传递给下游服务器，⽤于业务处理
        ServerHttpRequest request = exchange.getRequest().mutate()
                .header(requestHeadClientIp, new String[]{ip})
                .build();
        logger.info("访问⼊⼝过滤器RequestInfoFilter.");
        return chain.filter(exchange.mutate().request(request).build());
    }
}
