package com.myz.shardingjdbc.config.java;

import com.myz.shardingjdbc.api.ShardingJdbcManager;
import com.myz.shardingjdbc.config.ShardingJdbcJavaManager;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * 分表 demo_ds.t_order
 *
 * @author maoyz
 */
public class ShardingJdbcShardingTableJavaManager extends ShardingJdbcJavaManager {

    public static void main(String[] args) throws Exception {
        Map<String, DataSource> dataSourceMap = shardingTableDataSourceMap();
        ShardingRuleConfiguration shardingRule = shardingRuleConfigurationTable();

        Properties properties = prop();

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRule, properties);

        // for (int i = 0; i < 100; i++) {
        //     int i1 = ShardingJdbcManager.executeInsertSqlNoId(dataSource);
        //     System.out.println(i1);
        // }

        ShardingJdbcManager.executeQuerySqlShardingTableSingleTable(dataSource);

        System.out.println("分頁查詢");
        ShardingJdbcManager.executeQuerySqlShardingTablePageSingleTable(dataSource, 2, 3);
    }

}
