/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-02 11:01  Inc. All rights reserved.
 */
package com.myz.project.infrastructure.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

/**
 * @author maoyz
 */
@Component
public class ApplicationStartingEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(ApplicationStartingEventListener.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        ConfigurableEnvironment environment = context.getEnvironment();
        try {
            log.info("Application name : {}", environment.getProperty("spring.application.name"));
            log.info("Application profiles active : {}", environment.getProperty("spring.profiles.active"));
            log.info("ConfigCenter url : {}", environment.getProperty("spring.cloud.config.discovery.service-id"));
        } catch (Exception e) {
            log.warn("Get Properties Exception : {}", e.getMessage());
        }
    }
}