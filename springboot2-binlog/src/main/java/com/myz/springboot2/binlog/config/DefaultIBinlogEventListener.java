/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.config;

import com.myz.springboot2.binlog.domain.BinaryRowData;
import com.myz.springboot2.binlog.message.WaresBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author maoyz0621 on 2021/10/20
 * @version v1.0
 */
@Service
@Slf4j
public class DefaultIBinlogEventListener implements IBinlogEventListener {

    @Override
    public void register() {

    }

    @Override
    public void onEvent(BinaryRowData binaryRowData) {
        // todo
        Map<String, String> difference = WaresBuilder.diff(binaryRowData.getBefore().get(0), binaryRowData.getAfter().get(0));
        log.info("diff is {} - {}", binaryRowData.getTableTemplate().getTableName(), difference);
    }
}