/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取表信息
 *
 * @author maoyz0621 on 2021/8/25
 * @version v1.0
 */
@Data
public class TableTemplate {
    private String tableName;
    private String level;
    /**
     * 操作类型 -> 多列
     */
    private Map<OperationTypeEnum, List<String>> fieldSetMap = new HashMap<>();
    /**
     * 只会显示字段的索引
     * binlog 字段索引 -> 字段名称
     */
    private Map<Integer, String> posMap = new HashMap<>();
}