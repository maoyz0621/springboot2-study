/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-24 17:40  Inc. All rights reserved.
 */
package com.myz.shardingjdbc.type;

/**
 * @author maoyz
 */
public enum ShardingType {

    /**
     * 分库
     */
    SHARDING_DATABASES,

    /**
     * 分表
     */
    SHARDING_TABLES,

    /**
     * 分库分表
     */
    SHARDING_DATABASES_AND_TABLES,

    /**
     * 读写分离
     */
    MASTER_SLAVE,

    /**
     * 分库分表、读写分离
     */
    SHARDING_MASTER_SLAVE,

    ENCRYPT
}