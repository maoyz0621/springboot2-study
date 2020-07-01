package com.myz.shardingjdbc.config.java;

import com.myz.shardingjdbc.api.ShardingJdbcManager;
import com.myz.shardingjdbc.config.ShardingJdbcJavaManager;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * 分库 demo_ds_0 demo_ds_1
 *
 * @author maoyz
 */
public class ShardingJdbcShardingDbJavaManager extends ShardingJdbcJavaManager {

    public static void main(String[] args) throws Exception {
        Map<String, DataSource> dataSourceMap = shardingDbDataSourceMap();
        ShardingRuleConfiguration shardingRule = shardingRuleConfigurationDb();

        Properties properties = prop();

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRule, properties);

        for (int i = 0; i < 6; i++) {
            int i1 = ShardingJdbcManager.executeInsertSqlNoId(dataSource);
            System.out.println(i1);
        }

        // java.lang.IllegalStateException: Inline strategy cannot support range sharding.
        // ShardingJdbcManager.executeQuerySqlShardingTableSingleTable(dataSource);
        ShardingJdbcManager.executeQuerySqlShardingDb(dataSource);

        System.out.println("分页查询");
        // ShardingJdbcManager.executeQuerySqlShardingTablePageSingleTable(dataSource, 2, 3);
        ShardingJdbcManager.executeQuerySqlShardingDbPage(dataSource, 2, 3);
    }




}
