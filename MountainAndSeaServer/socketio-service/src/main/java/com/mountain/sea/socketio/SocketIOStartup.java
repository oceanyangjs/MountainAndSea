package com.mountain.sea.socketio;

import com.alibaba.fastjson.JSONObject;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.mountain.sea.core.exception.ExceptionEnum;
import com.mountain.sea.core.exception.RestApiException;
import com.mountain.sea.core.utils.*;
import com.mountain.sea.socketio.monitor.MonitorProcessor;
import com.mountain.sea.socketio.monitor.SocketRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/8 19:35
 */
@Component
public class SocketIOStartup implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(SocketIOStartup.class);
    private static final ConcurrentHashMap<String, SocketThread> socketThreadMap = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, String[]> userInfoMap = new ConcurrentHashMap<>();
    //    client与房间名关联关系
    private static final ConcurrentHashMap<SocketIOClient, String> clientRoomInfo = new ConcurrentHashMap<>();
    private PublicKey publicKey;
    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    private RedisUtils redisUtils;

    private void startUpServer() {
        bindListener();
        socketIOServer.startAsync();
    }

    private void bindListener() {
        connect();
        disconnect();
        bindEventListener();
    }

    private void connect() {
        socketIOServer.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                String token = socketIOClient.getHandshakeData().getSingleUrlParam(Constants.TOKEN_KEY);
//                todo 暂时移除权限认证
//                Map<String, Object> userInfo = JwtUtils.getUserInfoFromToken(token, publicKey);
//                String tenantName = (String) userInfo.get(Constants.CONTEXT_TENANT_NAME);
//                String tenantId = String.valueOf(userInfo.get(Constants.CONTEXT_TENANT_ID));
//                userInfoMap.put(socketIOClient.getSessionId().toString(), new String[]{tenantName, tenantId});
                logger.info("{}已连接", socketIOClient.getRemoteAddress().toString() + socketIOClient.getSessionId());
            }
        });

        socketIOServer.getNamespace("/online_debugging").addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                
            }
        });
    }

    private void disconnect() {
        socketIOServer.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient socketIOClient) {
                userInfoMap.remove(socketIOClient.getSessionId().toString());
                SocketThread st = socketThreadMap.get(socketIOClient.getSessionId().toString());
                if (st != null) {
                    st.setLoop(false);
                    socketThreadMap.remove(socketIOClient.getSessionId().toString());
                }
                logger.info("{}已关闭", socketIOClient.getSessionId());
            }
        });
    }

    private void bindEventListener() {

        socketIOServer.getNamespace("/model_data_monitor").addEventListener("cmd", Object.class, new DataListener<Object>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Object value, AckRequest ackRequest) throws Exception {
                System.out.println("receive message:" + value);
                String message =JSONObject.toJSONString((Map<String, Object>) value);
                JSONObject messageJson = JSONObject.parseObject(message);
                if ("test".equals(messageJson.getString("cmd"))) {
//                    messageJson.getJSONObject("message").put("sessionId", socketIOClient.getSessionId());
//                    处理数据
                    sendMessage(socketIOClient, value);
                }
            }
        });
    }

    private void sendMessage(SocketIOClient socketIOClient, Object object) {
        String cmd;
        String value;
        try {
            String message = JSONObject.toJSONString((Map<String, Object>) object);
            cmd = JsonPathUtils.parseString(message, "$.cmd");
//            JSONObject jsonObject = JsonPathUtils.parseOject(message, "$.property", JSONObject.class);
//            todo 由于移除了token验证，因此此处无法获得用户信息
//            String[] tenantInfo = userInfoMap.get(socketIOClient.getSessionId().toString());
//            jsonObject.put("tenantId", Long.parseLong(tenantInfo[1]));
//            jsonObject.put("tenantName", tenantInfo[0]);
            value = message;
        } catch (Exception e) {
            throw new RestApiException(ExceptionEnum.DATA_PARSE_EXCEPTION);
        }
//        具体逻辑的处理，利用策略模式
        SocketRoute route = SocketRoute.getSocketRoute(cmd);
        if (route == null) {
            throw new RuntimeException("找不到该cmd:" + cmd + " 对应的处理方法");
        }
        MonitorProcessor processor = ApplicationContextUtils.getBean(route.getMonitorClass());
        startThread(socketIOClient, processor, value, route.getIntervalTime());
    }

    private void startThread(SocketIOClient socketIOClient, MonitorProcessor processor, String value, long interval) {
        SocketThread st = socketThreadMap.get(socketIOClient.getSessionId().toString());
        if (st != null) {
            st.setLoop(false);
            socketThreadMap.remove(socketIOClient.getSessionId().toString());
        }
        SocketThread st_new = new SocketThread(socketIOClient, processor, value, interval);
        socketThreadMap.put(socketIOClient.getSessionId().toString(), st_new);
        Thread t = new Thread(st_new);
        t.start();
    }

    private PublicKey getPublicKey(byte[] bytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    class SocketThread implements Runnable {
        private SocketIOClient socketIOClient;
        private String value;
        private long interval;
        private boolean loop = true;
        private MonitorProcessor processor;

        public SocketThread(SocketIOClient socketIOClient, MonitorProcessor processor, String value, long interval) {
            this.socketIOClient = socketIOClient;
            this.value = value;
            this.interval = interval;
            this.processor = processor;
        }

        public void setLoop(boolean loop) {
            this.loop = loop;
        }

        @Override
        public void run() {
            while (loop && socketIOClient.isChannelOpen()) {
                Object returnVal = this.processor.process(value);
                if (loop && socketIOClient.isChannelOpen()) {
                    System.out.println("returnVal:" + returnVal);
                    socketIOClient.sendEvent("message", returnVal);
                }
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    logger.error("Socket clientId:{} {}", socketIOClient.getSessionId(), e.getMessage());
                }
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            String s = redisUtils.get(Constants.REDIS_USER_PUB_KEY).toString();
            this.publicKey = getPublicKey(RsaKeyUtils.toBytes(s));
            logger.info("====== publicKey Success Achieve！ ======");
            logger.info("====== publicKey 成功获取！ ======");
        } catch (Exception e) {
            logger.error("****** init pubKey failed！******", e);
            logger.error("****** 初始化加载用户pubKey失败！ ******", e);
        }
        startUpServer();
    }
}
