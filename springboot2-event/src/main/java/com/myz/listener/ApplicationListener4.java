/**
 * Copyright 2022 Inc.
 **/
package com.myz.listener;

import com.myz.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author maoyz0621 on 2022/1/22
 * @version v1.0
 */
@Component
@Slf4j
public class ApplicationListener4 {

    @Async
    @EventListener
    public void onApplicationEvent(MyEvent event) {
        log.info("ApplicationListener4 = {}", event);
    }
}