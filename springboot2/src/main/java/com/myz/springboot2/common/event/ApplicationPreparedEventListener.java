/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.common.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Iterator;

/**
 * 1 ApplicationPreparedEvent
 *
 * @author maoyz on 2018/8/23
 * @version: v1.0
 */
@Slf4j
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent applicationPreparedEvent) {
        ConfigurableApplicationContext context = applicationPreparedEvent.getApplicationContext();
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Iterator<String> beanNames = beanFactory.getBeanNamesIterator();
        while (beanNames.hasNext()) {
            String next = beanNames.next();
            log.debug("== ApplicationPreparedEvent beanNames {} ==" + next);
        }
        log.info("...... ApplicationPreparedEvent ......");
    }
}
