package com.mountain.sea.filter;

import com.mountain.sea.utils.StringUtils;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.gateway.config.GatewayLoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/22 13:13
 */
public class SocketIOLoadBalancerFilter implements GlobalFilter, Ordered {
    private static final String SOCKETIO_SERVICE_ID = "socketio-service";
    private static final String SOCKETIO_PATH = "/socket.io/";
    private static final int SOCKETIO_DEFAULT_PORT = 9922;
    private static final String SOCKETIO_PORT_META = "socketio-service";

    private final LoadBalancerClientFactory clientFactory;

    private GatewayLoadBalancerProperties properties;
    
    public SocketIOLoadBalancerFilter(LoadBalancerClientFactory clientFactory,
                                      GatewayLoadBalancerProperties properties) {
        this.clientFactory = clientFactory;
        this.properties = properties;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
        if (url == null || (!"lb".equals(url.getScheme()) && !"lb".equals(schemePrefix))) {
            return chain.filter(exchange);
        }
        // preserve the original url
        addOriginalRequestUrl(exchange, url);

        URI requestUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        String serviceId = requestUri.getHost();
        Set<LoadBalancerLifecycle> supportedLifecycleProcessors = LoadBalancerLifecycleValidator
                .getSupportedLifecycleProcessors(clientFactory.getInstances(serviceId, LoadBalancerLifecycle.class),
                        RequestDataContext.class, ResponseData.class, ServiceInstance.class);
        DefaultRequest<RequestDataContext> lbRequest = new DefaultRequest<>(
                new RequestDataContext(new RequestData(exchange.getRequest()), getHint(serviceId)));
        LoadBalancerProperties loadBalancerProperties = clientFactory.getProperties(serviceId);
        return choose(exchange).doOnNext(response -> {

            if (!response.hasServer()) {
                supportedLifecycleProcessors.forEach(lifecycle -> lifecycle
                        .onComplete(new CompletionContext<>(CompletionContext.Status.DISCARD, lbRequest, response)));
                throw NotFoundException.create(properties.isUse404(), "Unable to find instance for " + url.getHost());
            }

            ServiceInstance retrievedInstance = response.getServer();

            URI uri = exchange.getRequest().getURI();

            // if the `lb:<scheme>` mechanism was used, use `<scheme>` as the default,
            // if the loadbalancer doesn't provide one.
            String overrideScheme = retrievedInstance.isSecure() ? "https" : "http";
            if (schemePrefix != null) {
                overrideScheme = url.getScheme();
            }

            DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance(retrievedInstance,
                    overrideScheme);
            URI requestUrl = reconstructURI(serviceInstance, uri);
            URI newUri = null;
            try {
                newUri = new URI(requestUrl.toString().replace("2021","9922"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, newUri);
            exchange.getAttributes().put(GATEWAY_LOADBALANCER_RESPONSE_ATTR, response);
            supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onStartRequest(lbRequest, response));
        }).then(chain.filter(exchange))
                .doOnError(throwable -> supportedLifecycleProcessors.forEach(lifecycle -> lifecycle
                        .onComplete(new CompletionContext<ResponseData, ServiceInstance, RequestDataContext>(
                                CompletionContext.Status.FAILED, throwable, lbRequest,
                                exchange.getAttribute(GATEWAY_LOADBALANCER_RESPONSE_ATTR)))))
                .doOnSuccess(aVoid -> supportedLifecycleProcessors.forEach(lifecycle -> lifecycle
                        .onComplete(new CompletionContext<ResponseData, ServiceInstance, RequestDataContext>(
                                CompletionContext.Status.SUCCESS, lbRequest,
                                exchange.getAttribute(GATEWAY_LOADBALANCER_RESPONSE_ATTR), buildResponseData(exchange,
                                loadBalancerProperties.isUseRawStatusCodeInResponseData())))));
    }

    private ResponseData buildResponseData(ServerWebExchange exchange, boolean useRawStatusCodes) {
        if (useRawStatusCodes) {
            return new ResponseData(new RequestData(exchange.getRequest()), exchange.getResponse());
        }
        return new ResponseData(exchange.getResponse(), new RequestData(exchange.getRequest()));
    }

    protected URI reconstructURI(ServiceInstance serviceInstance, URI original) {
        return LoadBalancerUriTools.reconstructURI(serviceInstance, original);
    }

    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange) {
        URI requestUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        String serviceId = requestUri.getHost();
        DefaultRequest<RequestDataContext> lbRequest = new DefaultRequest<>(
                new RequestDataContext(new RequestData(exchange.getRequest()), getHint(serviceId)));
        ReactorLoadBalancer<ServiceInstance> loadBalancer = this.clientFactory.getInstance(serviceId,
                ReactorServiceInstanceLoadBalancer.class);
        if (loadBalancer == null) {
            throw new NotFoundException("No loadbalancer available for " + serviceId);
        }
//        supportedLifecycleProcessors.forEach(lifecycle -> lifecycle.onStart(lbRequest));
//        return loadBalancer.choose(lbRequest);
        return loadBalancer.choose(lbRequest);
//        Response<ServiceInstance> loadBalancerResponse = Mono.from(serviceInstance).block();
//        if (loadBalancerResponse == null) {
//            return null;
//        }
//        return loadBalancerResponse.getServer();
//        return serviceInstance.doOnSuccess(new Consumer<Response<ServiceInstance>>() {
//            @Override
//            public void accept(Response<ServiceInstance> serviceInstanceResponse) {
//                if (serviceInstanceResponse != null && SOCKETIO_SERVICE_ID.equals(serviceInstanceResponse.getServer().getServiceId())
//                        && exchange.getRequest().getPath().toString().contains(SOCKETIO_PATH)) {
//                    Map<String, String> map = serviceInstanceResponse.getServer().getMetadata();
//                    String socketioPort = map.get(SOCKETIO_PORT_META);
//                    int port;
//                    if (StringUtils.isEmpty(socketioPort)) {
//                        port = SOCKETIO_DEFAULT_PORT;
//                    } else {
//                        port = Integer.parseInt(socketioPort);
//                    }
//                    return new DefaultServiceInstance(serviceInstanceResponse.getServer().getInstanceId(), serviceInstanceResponse.getServer().getServiceId(),
//                            serviceInstanceResponse.getServer().getHost(), port, serviceInstanceResponse.getServer().isSecure());
//                }
//                return serviceInstanceResponse.getServer();
//            }
//        });
    }

    private String getHint(String serviceId) {
        LoadBalancerProperties loadBalancerProperties = clientFactory.getProperties(serviceId);
        Map<String, String> hints = loadBalancerProperties.getHint();
        String defaultHint = hints.getOrDefault("default", "default");
        String hintPropertyValue = hints.get(serviceId);
        return hintPropertyValue != null ? hintPropertyValue : defaultHint;
    }

    @Override
    public int getOrder() {
        return 10100;
    }
}
