/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.domain;

import lombok.Data;

import java.util.Map;

/**
 * 解析到java对象
 *
 * @author maoyz0621 on 2021/8/25
 * @version v1.0
 */
@Data
public class ParseTemplate {
    private String database;
    private Map<String, TableTemplate> tableTemplateMap;
}