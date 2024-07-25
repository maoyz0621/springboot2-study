/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.store;

import com.myz.log.record.entity.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class DefaultLogRecordServiceImpl extends AbstractLogRecordService {

    private final Logger log = LoggerFactory.getLogger(DefaultLogRecordServiceImpl.class);

    @Override
    public void record(Record record) {
        log.info(record.toString());
    }

}