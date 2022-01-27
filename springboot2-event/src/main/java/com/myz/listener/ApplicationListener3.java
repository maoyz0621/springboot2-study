/**
 * Copyright 2022 Inc.
 **/
package com.myz.listener;

import com.myz.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author maoyz0621 on 2022/1/22
 * @version v1.0
 */
@Component
@Slf4j
public class ApplicationListener3<ApplicationEvent> {

    /**
     * 通过classes指定监听的Event
     *
     * @param event
     * @throws InterruptedException
     */
    @EventListener(classes = MyEvent.class)
    @Order(2)
    public void onEvent(ApplicationEvent event) throws InterruptedException {
        Thread.sleep(1000);
        log.info("ApplicationListener3-2 = {}", event);
    }

    @EventListener(classes = MyEvent.class)
    @Order(1)
    public void onEvent1(ApplicationEvent event) throws InterruptedException {
        Thread.sleep(1000);
        log.info("ApplicationListener3-1 = {}", event);
    }
}