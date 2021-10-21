/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.config;

import com.myz.springboot2.binlog.domain.BinaryRowData;

/**
 * @author maoyz0621 on 2021/8/29
 * @version v1.0
 */
public interface IBinlogEventListener {

    void register();

    void onEvent(BinaryRowData binaryRowData);
}