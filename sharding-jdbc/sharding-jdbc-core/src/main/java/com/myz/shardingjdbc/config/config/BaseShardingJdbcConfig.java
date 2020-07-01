/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-24 18:06  Inc. All rights reserved.
 */
package com.myz.shardingjdbc.config.config;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author maoyz
 */
public interface BaseShardingJdbcConfig {

    DataSource getDataSource() throws SQLException;

}