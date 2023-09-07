/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.config;

import lombok.Data;

/**
 * @author maoyz0621 on 2023/7/4
 * @version v1.0
 */
@Data
public class SecurityMaskConfigProperty {

    private String tableName;

    private String columnName;

    private String convertType;
}