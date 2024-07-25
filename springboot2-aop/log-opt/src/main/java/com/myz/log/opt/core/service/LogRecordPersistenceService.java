/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.service;


import com.myz.log.opt.core.model.LogRecord;

/**
 * 操作日志持久化
 *
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public interface LogRecordPersistenceService {

    /**
     * 持久化操作日志
     *
     * @param logRecord 日志记录
     */
    void doLogRecordPersistence(LogRecord logRecord);

}