/**
 * Copyright 2021 Inc.
 **/
package com.myz.config.enable.monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@ConfigurationProperties(prefix = "spring.server.monitor")
public class ServerLoadMonitorProperties {

    private boolean enables = true;

    public boolean isEnables() {
        return enables;
    }

    public void setEnables(boolean enables) {
        this.enables = enables;
    }
}