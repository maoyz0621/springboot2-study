/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.store;

import com.myz.log.record.entity.Record;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public interface ILogRecordService {

    /**
     * 保存 log
     *
     * @param record 日志实体
     */
    void record(Record record);
}