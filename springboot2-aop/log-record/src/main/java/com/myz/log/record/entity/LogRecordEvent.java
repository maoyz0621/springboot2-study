/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.entity;

import org.springframework.context.ApplicationEvent;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class LogRecordEvent extends ApplicationEvent {

    public LogRecordEvent(Record source) {
        super(source);
    }

}