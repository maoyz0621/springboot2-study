package com.myz.shardingjdbc.config.yml;

import com.myz.shardingjdbc.api.ShardingJdbcManager;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;

/**
 * yml文件配置
 *
 * @author maoyz
 */
public class ShardingJdbcShardingTableYamlManager {

    public static void main(String[] args) throws Exception {

        // 创建数据源
        DataSource dataSource = YamlShardingDataSourceFactory.createDataSource(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "sharding-table-jdbc.yml"));
        // for (int i = 0; i < 20; i++) {
        //     int i1 = ShardingJdbcManager.executeInsertSqlNoId(dataSource);
        //     System.out.println(i1);
        // }

        ShardingJdbcManager.executeQuerySqlShardingTableSingleTable(dataSource);

        System.out.println("分頁查詢");
        ShardingJdbcManager.executeQuerySqlShardingTablePageSingleTable(dataSource,2,3);
    }

}