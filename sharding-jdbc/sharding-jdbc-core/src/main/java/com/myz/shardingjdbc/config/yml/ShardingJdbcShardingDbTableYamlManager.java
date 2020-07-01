package com.myz.shardingjdbc.config.yml;

import com.myz.shardingjdbc.api.ShardingJdbcManager;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;

/**
 *分库、分表 demo_ds_0.t_order   demo_ds_1.t_order
 *
 * @author maoyz
 */
public class ShardingJdbcShardingDbTableYamlManager {

    public static void main(String[] args) throws Exception {

        // 创建数据源
        DataSource dataSource = YamlShardingDataSourceFactory.createDataSource(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "META-INF/sharding-db-table-jdbc.yml"));
        for (int i = 0; i < 8; i++) {
            int i1 = ShardingJdbcManager.executeInsertSqlNoId(dataSource);
            System.out.println(i1);
        }

        // java.lang.IllegalStateException: Inline strategy cannot support range sharding.
        ShardingJdbcManager.executeQuerySqlShardingDb(dataSource);

        System.out.println("分頁查詢");
        ShardingJdbcManager.executeQuerySqlShardingDbPage(dataSource, 2, 3);
    }

}