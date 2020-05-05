/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.common.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Iterator;

/**
 * 2 ApplicationStartedEvent
 *
 * @author maoyz on 2018/8/23
 * @version: v1.0
 */
@Slf4j
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Iterator<String> beanNames = beanFactory.getBeanNamesIterator();
        while (beanNames.hasNext()) {
            String next = beanNames.next();
            log.debug("== ApplicationStartedEvent beanNames {} ==" + next);
        }
        log.info("...... ApplicationStartedEvent ......");
    }
}
