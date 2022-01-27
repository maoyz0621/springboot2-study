/**
 * Copyright 2022 Inc.
 **/
package com.myz.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 2022/1/23
 * @version v1.0
 */
@Component
public class SpringEventListener implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {
    private static Logger log = LoggerFactory.getLogger(SpringEventListener.class);

    private static BlockingDeque<Runnable> contextRefreshEventActions = new LinkedBlockingDeque<>();
    private static volatile boolean isStarted = true;

    public SpringEventListener() {
        isStarted = false;
        Thread eventListenerThread = new Thread(this.getEventListenerTask(), "SpringEventListener");
        // 守护线程
        eventListenerThread.setDaemon(true);
        eventListenerThread.start();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        isStarted = true;
    }

    public static void addContextRefreshEventAction(Runnable action) {
        if (isStarted && contextRefreshEventActions.size() == 0) {
            action.run();
        } else {
            contextRefreshEventActions.add(action);
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private Runnable getEventListenerTask() {
        return () -> {
            while (true) {
                if (!isStarted) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(50L);
                    } catch (InterruptedException e) {
                        log.warn("", e);
                    }
                }
                Runnable poll = null;
                try {
                    poll = SpringEventListener.contextRefreshEventActions.poll(300L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    log.warn("", e);
                }

                if (poll == null) {
                    return;
                }
                poll.run();
            }
        };
    }
}