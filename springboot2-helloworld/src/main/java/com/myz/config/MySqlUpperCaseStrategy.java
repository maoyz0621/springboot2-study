/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.config;

import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * 自定义mysql命名策略
 *
 * @author maoyz on 2018/7/24
 * @version: v1.0
 */
public class MySqlUpperCaseStrategy extends SpringPhysicalNamingStrategy {

    @Override
    protected boolean isCaseInsensitive(JdbcEnvironment jdbcEnvironment) {
        return false;
    }
}

