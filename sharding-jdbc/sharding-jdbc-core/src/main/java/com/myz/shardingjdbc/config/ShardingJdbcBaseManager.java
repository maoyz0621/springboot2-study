package com.myz.shardingjdbc.config;

/**
 * @author maoyz
 */
public abstract class ShardingJdbcBaseManager {

    public static final String url = "jdbc:mysql://localhost:3306/%s?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai";
}
