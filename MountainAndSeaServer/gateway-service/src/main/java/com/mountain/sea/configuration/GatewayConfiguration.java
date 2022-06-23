package com.mountain.sea.configuration;

import com.mountain.sea.filter.AuthenticationTokenFilter;
import com.mountain.sea.filter.RequestInfoFilter;
import com.mountain.sea.filter.SocketIOLoadBalancerFilter;
import com.mountain.sea.filter.WebsocketLoadBalancerFilter;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-05-31 16:29
 */
@Configuration
public class GatewayConfiguration {

    @Bean
    public RequestInfoFilter requestInfoFilter(){
        return new RequestInfoFilter();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilter(GatewayFilterProperties gatewayFilterProperties){
//        return new AuthenticationTokenFilter(gatewayFilterProperties,redisTemplate);
        return new AuthenticationTokenFilter(gatewayFilterProperties);
    }

//    @Bean
//    public SocketIOLoadBalancerFilter loadBalancerClientFilter(LoadBalancerClientFactory client,
//                                                               GatewayLoadBalancerProperties properties) {
//        return new SocketIOLoadBalancerFilter(client, properties);
//    }

    @Bean
    public WebsocketLoadBalancerFilter websocketLoadBalancerFilter(LoadBalancerClientFactory client,
                                                                GatewayLoadBalancerProperties properties) {
        return new WebsocketLoadBalancerFilter(client, properties);
    }

//    @Bean
//    public SocketIOLoadBalancerReactiveFilter socketIOLoadBalancerReactiveFilter(LoadBalancerClientFactory clientFactory, GatewayLoadBalancerProperties properties) {
//        return new SocketIOLoadBalancerReactiveFilter(clientFactory, properties);
//    }
}
