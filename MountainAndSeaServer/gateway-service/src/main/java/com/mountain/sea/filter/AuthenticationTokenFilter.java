package com.mountain.sea.filter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-05-31 16:45
 */
public class AuthenticationTokenFilter implements GlobalFilter, CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return null;
    }
//    @Override
//    public void run(String... args) throws Exception {
//        boolean key = true;
////        修改为反复获取
//        while (key){
//            try {
//                // 获取公钥
//                String s = redisUtils.get(Constants.REDIS_USER_PUB_KEY).toString();
//                this.publicKey = getPublicKey(RsaKeyUtils.toBytes(s));
//                logger.info("====== publicKey Success Achieve！ ======");
//                logger.info("====== publicKey 成功获取！ ======");
//                key = false;
//            } catch (Exception e) {
//                logger.error("****** init pubKey failed！******", e);
//                logger.error("****** 初始化加载用户pubKey失败！ ******", e);
//                Thread.sleep(30*1000);
//                logger.info("====== publicKey 重新获取中！ ======");
//            }
//        }
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest request = exchange.getRequest();
//        String path = request.getPath().value();
//        if (!CollectionUtils.isEmpty(properties.getIgnoreUrl())) {
//            boolean flag = false;
//            for (String s : properties.getIgnoreUrl()) {
//                s = s.replace("/**",".*");
//                flag = Pattern.matches(s,path);
//                if(flag) {
//                    return chain.filter(exchange);
//                }
//            }
//        }
//        try {
//            return checkAuthenticationToken(exchange, chain);
//        } catch (Exception e) {
//            return writeAuthenticationFailure(exchange.getResponse(), Constants.INVALID_AUTH_MESSAGE);
//        }
//    }
}
