package com.mountain.sea.configuration;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ExceptionListener;
import com.mountain.sea.core.utils.RedisUtils;
import com.mountain.sea.socketio.TokenAuthorizationListener;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/8 19:31
 */
@ConfigurationProperties(prefix = "socketio")
@Configuration
public class SocketIOConfiguration {
    private int port;
    private List<String> customNamespaces;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<String> getCustomNamespaces() {
        return customNamespaces;
    }

    public void setCustomNamespaces(List<String> customNamespaces) {
        this.customNamespaces = customNamespaces;
    }

    private static final Logger logger = LoggerFactory.getLogger(SocketIOConfiguration.class);

    @Bean
    public TokenAuthorizationListener tokenAuthorizationListener(RedisUtils redisUtils){
        return new TokenAuthorizationListener(redisUtils);
    }

    @Bean("socketIOServer")
    public SocketIOServer socketIOServer(TokenAuthorizationListener tokenAuthorizationListener) throws UnknownHostException {
        com.corundumstudio.socketio.Configuration configuration = new com.corundumstudio.socketio.Configuration();
        configuration.setPort(port);
        configuration.setAuthorizationListener(tokenAuthorizationListener);
        configuration.setExceptionListener(new ExceptionListener() {
            @Override
            public void onEventException(Exception e, List<Object> list, SocketIOClient socketIOClient) {
                logger.error("客户端:" + socketIOClient.getRemoteAddress() + " EventException：" +e.getMessage());
            }

            @Override
            public void onDisconnectException(Exception e, SocketIOClient socketIOClient) {
                logger.error("客户端:" + socketIOClient.getRemoteAddress() + " DisconnectException：" +e.getMessage());
            }

            @Override
            public void onConnectException(Exception e, SocketIOClient socketIOClient) {
                logger.error("客户端:" + socketIOClient.getRemoteAddress() + " ConnectException：" +e.getMessage());
            }

            @Override
            public void onPingException(Exception e, SocketIOClient socketIOClient) {
                logger.error("客户端:" + socketIOClient.getRemoteAddress() + " PingException：" +e.getMessage());
            }

            @Override
            public boolean exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
                return true;
            }
        });
//        configuration.setOrigin("*");
//        configuration.setSocketConfig();
//        configuration.setWorkerThreads(1);
//         configuration.setAllowCustomRequests(true);
//        configuration.setUpgradeTimeout(10000);
//        configuration.setPingTimeout(60000);
//        configuration.setPingInterval(25000);
//        configuration.setMaxHttpContentLength(2071738);
//        configuration.setMaxFramePayloadLength(2071738);
        SocketIOServer socketIOServer = new SocketIOServer(configuration);
//        初始化namespaces
        for (String customNamespace : customNamespaces) {
            socketIOServer.addNamespace(customNamespace);
        }
        return socketIOServer;
    }
}
