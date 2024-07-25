/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.store;

import com.myz.log.record.entity.LogRecordEvent;
import com.myz.log.record.entity.Record;
import org.springframework.context.ApplicationListener;

/**
 * 日志持久存储服务接口
 *
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public abstract class AbstractLogRecordService implements ApplicationListener<LogRecordEvent>, ILogRecordService {

    @Override
    public void onApplicationEvent(LogRecordEvent event) {
        record((Record) event.getSource());
    }

}