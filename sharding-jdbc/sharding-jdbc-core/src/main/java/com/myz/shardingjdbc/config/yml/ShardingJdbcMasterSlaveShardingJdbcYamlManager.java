package com.myz.shardingjdbc.config.yml;

import com.myz.shardingjdbc.api.ShardingJdbcManager;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlShardingDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;

/**
 * yml文件配置
 *
 * @author maoyz
 */
public class ShardingJdbcMasterSlaveShardingJdbcYamlManager {

    public static void main(String[] args) throws Exception {

        // 创建数据源
        DataSource dataSource = YamlShardingDataSourceFactory.createDataSource(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "META-INF/master-slave-sharding.yml"));
        for (int i = 0; i < 10; i++) {
            System.out.println("\r\n");
            ShardingJdbcManager.executeQuerySqlShardingTableSingleTable(dataSource);
        }

        System.out.println("insert\r\n");
        // insert
        for (int i = 0; i < 5; i++) {
            int i1 = ShardingJdbcManager.executeInsertSqlNoId(dataSource);
            System.out.println(i1);
        }
    }

}