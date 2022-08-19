/**
 * Copyright 2022 Inc.
 **/
package com.myz.healthcheck.client.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 2022/1/23
 * @version v1.0
 */
@Component
public class HealthCheckMonitor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthCheckMonitor.class);
    private static final ScheduledExecutorService SCHEDULED_EXECUTOR = new ScheduledThreadPoolExecutor(1);

    public void init() {
        SCHEDULED_EXECUTOR.scheduleAtFixedRate(HealthCheckMonitor.this::healthCheck, 60, 60, TimeUnit.SECONDS);
    }

    // @PreDestroy
    public void destroy() {
        SCHEDULED_EXECUTOR.shutdown();
    }

    private void healthCheck() {
        Map<String, Map<String, Health>> healthCheckMap = HealthComponentManager.healthCheck();
        Iterator<Map.Entry<String, Map<String, Health>>> iterator = healthCheckMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Map<String, Health>> next = iterator.next();

            String type = next.getKey();
            Map<String, Health> value = next.getValue();
            Iterator<Map.Entry<String, Health>> iterator1 = value.entrySet().iterator();

            while (iterator1.hasNext()) {
                Map.Entry<String, Health> next1 = iterator1.next();
                String name = next1.getKey();
                Health health = next1.getValue();
                if (Status.UP.getCode().equals(health.getStatus().getCode())) {
                    LOGGER.info("{}_{},health status = {}, details = {}", new Object[]{type, name, health.getStatus(), health.getDetails()});
                } else {
                    LOGGER.warn("{}_{},health status = {}, details = {}", new Object[]{type, name, health.getStatus(), health.getDetails()});
                }
            }
        }

    }
}