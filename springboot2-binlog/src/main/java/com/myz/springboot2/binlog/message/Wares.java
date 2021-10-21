/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.message;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author maoyz0621 on 2021/8/29
 * @version v1.0
 */
@Data
public class Wares implements Serializable {
    private static final long serialVersionUID = -3062594113645632382L;
    // 摘要
    private String schema;
    // 数据库表
    private String table;
    private Boolean isDdl;
    private Map<String, String> beforeColumn;
    private Map<String, String> afterColumn;
    private Map<String, String> changeColumn;
    private List<String> pkNames;
    private String eventType;
    private Map<String, String> mysqlType;
    private Long binlogPosition;
    private String binlogFileName;
}