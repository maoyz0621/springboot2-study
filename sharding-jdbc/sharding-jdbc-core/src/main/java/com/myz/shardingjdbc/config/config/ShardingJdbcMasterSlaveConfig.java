package com.myz.shardingjdbc.config.config;

import com.myz.shardingjdbc.api.ShardingJdbcManager;
import com.myz.shardingjdbc.config.ShardingJdbcJavaManager;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * 读写分离： ds_master    ds_slave0    ds_slave1   ds_slave2
 *
 * @author maoyz
 */
public class ShardingJdbcMasterSlaveConfig implements BaseShardingJdbcConfig{


    @Override
    public DataSource getDataSource() throws SQLException {
        return null;
    }
}
