/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.starter;

import com.myz.distributed.lock.condition.ZookeeperCondition;
import com.myz.distributed.lock.executor.ZookeeperDistributedLockExecutor;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 通过条件来控制bean的注册 @Conditional
 * @author maoyz0621 on 2022/5/11
 * @version v1.0
 */
@Conditional(ZookeeperCondition.class)
@Configuration
@EnableConfigurationProperties(value = ZooKeeperProperty.class)
class ZooKeeperAutoConfiguration {

    private final ZooKeeperProperty zooKeeperProperty;

    public ZooKeeperAutoConfiguration(ZooKeeperProperty zooKeeperProperty) {
        this.zooKeeperProperty = zooKeeperProperty;
    }

    @Bean(initMethod = "start", destroyMethod = "close")
    @ConditionalOnMissingBean(CuratorFramework.class)
    @Order(200)
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(zooKeeperProperty.getBaseSleepTime(), zooKeeperProperty.getMaxRetries());
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zooKeeperProperty.getServers())
                .connectionTimeoutMs(zooKeeperProperty.getConnectionTimeout())
                .sessionTimeoutMs(zooKeeperProperty.getSessionTimeout())
                .retryPolicy(retryPolicy)
                .build();
        return client;
    }

    @Bean
    @Order(500)
    public ZookeeperDistributedLockExecutor zookeeperDistributedLockExecutor(CuratorFramework curatorFramework) {
        return new ZookeeperDistributedLockExecutor(curatorFramework);
    }

}