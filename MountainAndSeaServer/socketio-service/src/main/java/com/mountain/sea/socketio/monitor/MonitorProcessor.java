package com.mountain.sea.socketio.monitor;

/**
 * 操作逻辑接口
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/9 9:04
 */
public interface MonitorProcessor {
    Object process(String value);
}
