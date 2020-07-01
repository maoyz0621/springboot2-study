/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-24 17:39  Inc. All rights reserved.
 */
package com.myz.shardingjdbc.config.factory;

import com.myz.shardingjdbc.config.config.ShardingJdbcMasterSlaveConfig;
import com.myz.shardingjdbc.type.ShardingType;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author maoyz
 */
public class DataSourceFactory {

    public static DataSource newInstance(ShardingType shardingType) throws SQLException {
        switch (shardingType) {
            case ENCRYPT:
            case SHARDING_TABLES:
            case MASTER_SLAVE:
                return new ShardingJdbcMasterSlaveConfig().getDataSource();
            case SHARDING_DATABASES:
            case SHARDING_MASTER_SLAVE:
            case SHARDING_DATABASES_AND_TABLES:
            default:
        }
    }
}