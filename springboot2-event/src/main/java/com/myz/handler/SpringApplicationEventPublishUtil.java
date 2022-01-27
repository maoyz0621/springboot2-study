/**
 * Copyright 2022 Inc.
 **/
package com.myz.handler;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author maoyz0621 on 2022/1/23
 * @version v1.0
 */
@Component
public class SpringApplicationEventPublishUtil implements ApplicationContextAware {

    /**
     * 事件发布者
     */
    private static ApplicationEventPublisher publisher;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringApplicationEventPublishUtil.this.publisher = applicationContext;
    }

    public static void publishEvent(ApplicationEvent event) {
        publisher.publishEvent(event);
    }
}