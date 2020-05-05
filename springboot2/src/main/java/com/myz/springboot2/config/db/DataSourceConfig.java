/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.config.db;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 读取.yml配置文件属性
 *
 * @author maoyz on 18-10-29
 * @version: v1.0
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.druid")
@Data
public class DataSourceConfig {

    private int initialSize;
    private long maxActive;
    private int minIdle;
    private long maxWait;
    private boolean poolPreparedStatements;
    private String maxPoolPreparedStatementPerConnectionSize;
    private String validationQuery;
    private String testOnBorrow;
    private String testOnReturn;
    private String testWhileIdle;
    private String timeBetweenEvictionRunsMillis;
    private String minEvictableIdleTimeMillis;
    private Map<String, String> filter;
}
