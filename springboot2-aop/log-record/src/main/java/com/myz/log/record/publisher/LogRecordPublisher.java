/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.publisher;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 日志写入事件发布器,TODO 可以改为扩展点，使用户可以扩展发布的类型，例如通过 MQ
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
@Component
public class LogRecordPublisher implements ApplicationEventPublisher {

    @Resource
    ApplicationContext applicationContext;

    @Override
    public void publishEvent(Object event) {
        applicationContext.publishEvent(event);
    }

}
