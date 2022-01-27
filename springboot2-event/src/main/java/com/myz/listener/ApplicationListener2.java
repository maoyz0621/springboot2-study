/**
 * Copyright 2022 Inc.
 **/
package com.myz.listener;

import com.myz.event.MyEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * @author maoyz0621 on 2022/1/22
 * @version v1.0
 */
@Slf4j
public class ApplicationListener2 implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info("ApplicationListener2 = {}", event);
    }
}