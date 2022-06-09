package com.mountain.sea.socketio.monitor.impl;

import com.mountain.sea.socketio.monitor.MonitorProcessor;
import org.springframework.stereotype.Component;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022/6/9 9:07
 */
@Component
public class TestMonitor implements MonitorProcessor {
    @Override
    public Object process(String value) {
        return String.format("hello socketIO :%s",value);
    }
}
