package com.mountain.sea.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Yang Jingsheng
 * @version 1.0
 * @date 2022-05-31 16:56
 */
@Component
@ConfigurationProperties(prefix = "gateway")
public class GatewayFilterProperties {
    private List<String> ignoreUrl;

    public List<String> getIgnoreUrl() {
        return ignoreUrl;
    }

    public void setIgnoreUrl(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }
}
