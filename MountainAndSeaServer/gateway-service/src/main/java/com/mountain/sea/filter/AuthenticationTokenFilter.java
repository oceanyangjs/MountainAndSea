package com.mountain.sea.filter;

import cn.hutool.json.JSONUtil;
import com.mountain.sea.configuration.GatewayFilterProperties;
import com.mountain.sea.response.ExceptionEnum;
import com.mountain.sea.response.Result;
import com.mountain.sea.utils.FilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sun.tools.java.Constants;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-05-31 16:45
 */
public class AuthenticationTokenFilter implements GlobalFilter, CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(AuthenticationTokenFilter.class);
    private GatewayFilterProperties properties;
    public AuthenticationTokenFilter(GatewayFilterProperties gatewayFilterProperties) {
        this.properties = gatewayFilterProperties;
    }

    @Override
    public void run(String... args) throws Exception {
//        预置用于启动阶段redis初始化，用于jwt校验使用
        logger.info("command line runner");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("token验证----");
        ServerHttpRequest request = exchange.getRequest();
        String value = request.getPath().value();
        if(!CollectionUtils.isEmpty(properties.getIgnoreUrl())){
            boolean flag;
            for (String path:properties.getIgnoreUrl()) {
                path = path.replace("/**",".*");
                flag = Pattern.matches(path,value);
                if(flag){
                    return chain.filter(exchange);
                }
            }
        }
        return checkAuthenticationToken(exchange,chain);
    }

    /**
     * 校验逻辑
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> checkAuthenticationToken(ServerWebExchange exchange, GatewayFilterChain chain){
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest request = exchange.getRequest();
        String auth_token = "";
        Map<String,Object> userInfo = new HashMap<>();
//        验证失败逻辑
        if(1 == 1){
            return writeAuthenticationFailure(response,"验证异常");
        }
        return chain.filter(FilterUtils.getServerWebExchange(exchange,
                FilterUtils.getHttpRequest(request,
                        FilterUtils.getHttpHeadersConsumer(userInfo, auth_token))));
    }

    /**
     * 验证异常处理
     * @param response
     * @param message
     * @return
     */
    private Mono<Void> writeAuthenticationFailure(ServerHttpResponse response, String... message) {
        String responseInfo = generateAuthenticationFailureInfo(message);
        byte[] bytes = null;
        try {
            bytes = responseInfo.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
        response.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        return response.writeWith(Mono.just(dataBuffer));
    }

    private String generateAuthenticationFailureInfo(String... message) {
        Result result;
        if (message == null) {
            result = Result.builder().success(false).error(ExceptionEnum.REQUEST_NOT_ACCESS).build();
        } else {
            result = Result.builder().success(false).error(ExceptionEnum.REQUEST_NOT_ACCESS.getErrorCode(), message[0]).build();
        }
        return JSONUtil.toJsonStr(result);
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
