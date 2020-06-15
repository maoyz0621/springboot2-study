/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.common.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Iterator;

/**
 * 3 ApplicationReadyEvent
 *
 * @author maoyz on 2018/8/23
 * @version: v1.0
 */
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Iterator<String> beanNames = beanFactory.getBeanNamesIterator();
        while (beanNames.hasNext()) {
            String next = beanNames.next();
            log.debug("== ApplicationReadyEvent beanNames {} ==" + next);
        }
        log.info("...... ApplicationReadyEvent ......");
    }
}
