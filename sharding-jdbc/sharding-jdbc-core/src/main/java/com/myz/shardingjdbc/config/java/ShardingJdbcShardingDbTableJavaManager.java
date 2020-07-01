package com.myz.shardingjdbc.config.java;

import com.myz.shardingjdbc.api.ShardingJdbcManager;
import com.myz.shardingjdbc.config.ShardingJdbcJavaManager;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

/**
 * 分库、分表 demo_ds_0.t_order   demo_ds_1.t_order
 *
 * @author maoyz
 */
public class ShardingJdbcShardingDbTableJavaManager extends ShardingJdbcJavaManager {

    public static void main(String[] args) throws Exception {
        Map<String, DataSource> dataSourceMap = shardingDbDataSourceMap();
        ShardingRuleConfiguration shardingRule = shardingRuleConfigurationDbTable();

        Properties properties = new Properties();
        properties.setProperty("sql.show", "true");
        properties.setProperty("executor.size", "1000");

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRule, properties);

        for (int i = 0; i < 10; i++) {
            int i1 = ShardingJdbcManager.executeInsertSqlNoId(dataSource);
            System.out.println(i1);
        }

        // java.lang.IllegalStateException: Inline strategy cannot support range sharding.
        ShardingJdbcManager.executeQuerySqlShardingDb(dataSource);

        System.out.println("分頁查詢");
        ShardingJdbcManager.executeQuerySqlShardingDbPage(dataSource, 2, 3);
    }

}
