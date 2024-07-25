/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.service.impl;

import com.myz.log.opt.core.model.LogRecord;
import com.myz.log.opt.core.service.LogRecordPersistenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public class DefaultLogRecordPersistenceServiceImpl implements LogRecordPersistenceService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultLogRecordPersistenceServiceImpl.class);

    @Override
    public void doLogRecordPersistence(LogRecord logRecord) {
        if (Objects.nonNull(logRecord)) {
            logger.info("0={======> {} <======}=0", logRecord);
        }
    }
}