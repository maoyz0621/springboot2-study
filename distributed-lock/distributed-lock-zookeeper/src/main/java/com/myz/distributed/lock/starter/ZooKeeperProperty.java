/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author maoyz0621 on 2022/5/11
 * @version v1.0
 */
@ConfigurationProperties(prefix = "spring.coordinate.zookeeper")
public class ZooKeeperProperty {

    /**
     * zk连接集群，多个用逗号隔开
     */
    private String servers = "127.0.0.1:2181";

    /**
     * 会话超时时间
     */
    private int sessionTimeout = 60000;

    /**
     * 连接超时时间
     */
    private int connectionTimeout = 15000;

    /**
     * 初始重试等待时间(毫秒)
     */
    private int baseSleepTime = 1000;

    /**
     * 重试最大次数
     */
    private int maxRetries = 10;

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getBaseSleepTime() {
        return baseSleepTime;
    }

    public void setBaseSleepTime(int baseSleepTime) {
        this.baseSleepTime = baseSleepTime;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }
}