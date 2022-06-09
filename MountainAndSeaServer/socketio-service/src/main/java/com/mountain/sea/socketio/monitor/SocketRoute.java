package com.mountain.sea.socketio.monitor;

import com.mountain.sea.socketio.monitor.impl.TestMonitor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/9 9:06
 */
public enum SocketRoute {
    DEVICE_DATA_MONITOR("test",  TestMonitor.class, 2000);
    private static final Map<String, SocketRoute> routes = new HashMap<>();

    static {
        for (SocketRoute route : SocketRoute.values()) {
            routes.put(route.getCmd(),route);
        }
    }

    private Class<? extends MonitorProcessor> monitorClass;
    private long intervalTime;
    private String cmd;

    SocketRoute(String cmd, Class<? extends MonitorProcessor> monitorClass, int intervalTime) {
        this.cmd = cmd;
        this.monitorClass = monitorClass;
        this.intervalTime = intervalTime;
    }


    public Class<? extends MonitorProcessor> getMonitorClass() {
        return monitorClass;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public String getCmd() {
        return cmd;
    }

    public  static SocketRoute getSocketRoute(String cmd){
        return routes.get(cmd);
    }
}
