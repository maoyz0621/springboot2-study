/**
 * Copyright 2021 Inc.
 **/
package com.myz.config.enable.monitor.config;

import com.myz.config.enable.monitor.ServerLoadMonitorRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * 服务器负载监控配置类，不使用@Configuration
 *
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@EnableConfigurationProperties(ServerLoadMonitorProperties.class)
public class ServerLoadMonitorAutoConfig {

    private static final Logger log = LoggerFactory.getLogger(ServerLoadMonitorAutoConfig.class);

    private final ServerLoadMonitorProperties serverLoadMonitorProperties;

    public ServerLoadMonitorAutoConfig(ServerLoadMonitorProperties serverLoadMonitorProperties) {
        this.serverLoadMonitorProperties = serverLoadMonitorProperties;
    }


    @PostConstruct
    protected void init() {
        if (serverLoadMonitorProperties.isEnables()) {
            log.info("start server monitor ............");
        }
    }

    /**
     * 是否开放监控功能Bean
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.server.monitor", name = "enabled", matchIfMissing = false)
    public ServerLoadMonitorRunner serverLoadMonitorRunner() {
        return new ServerLoadMonitorRunner();
    }
}