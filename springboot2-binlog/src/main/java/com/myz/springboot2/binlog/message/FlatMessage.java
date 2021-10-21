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
public class FlatMessage implements Serializable {
    private static final long serialVersionUID = 8487961026046035223L;

    private long id;
    private String database;
    private String table;
    private List<String> pkNames;
    private Boolean isDdl;
    private String eventType;
    private String sql;
    private Map<String, Integer> sqlType;
    private Map<String, String> mysqlType;

    private List<Map<String, String>> data;
    private List<Map<String, String>> old;
    private String logFileName;
    private Long logFileOffset;
}