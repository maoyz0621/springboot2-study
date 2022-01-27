/**
 * Copyright 2022 Inc.
 **/
package com.myz.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * @author maoyz0621 on 2022/1/22
 * @version v1.0
 */
@Slf4j
public abstract class BaseEvent<T> extends ApplicationEvent {

    public BaseEvent(T source) {
        super(source);
        log.info("BaseEvent = {}", source);
    }
}