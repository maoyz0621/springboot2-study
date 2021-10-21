/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.binlog.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author maoyz0621 on 2021/9/13
 * @version v1.0
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchemaColumnsDo {

    private String tableSchema;
    private String tableName;
    private String columnName;
    private Integer ordinalPosition;
    private Object columnDefault;
    private String isNullable;
    private String dataType;
    private Object characterMaximumLength;
    private Object characterOctetLength;
    private Integer numericPrecision;
    private Integer numericScale;
    private Object characterSetName;
    private Object collationName;
}