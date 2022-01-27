/**
 * Copyright 2022 Inc.
 **/
package com.myz.listener;

import com.myz.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author maoyz0621 on 2022/1/22
 * @version v1.0
 */
@Component
@Slf4j
public class ApplicationListener1 implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info("ApplicationListener1 = {}", event);
    }
}