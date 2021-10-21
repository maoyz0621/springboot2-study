/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.domain;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author maoyz0621 on 2021/8/25
 * @version v1.0
 */
@Data
public class BinaryRowData {
    private TableTemplate tableTemplate;
    private EventType eventType;
    private List<Map<String, String>> before;
    private List<Map<String, String>> after;
}