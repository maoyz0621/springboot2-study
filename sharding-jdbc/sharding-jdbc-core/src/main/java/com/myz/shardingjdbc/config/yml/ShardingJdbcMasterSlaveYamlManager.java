package com.myz.shardingjdbc.config.yml;

import com.myz.shardingjdbc.api.ShardingJdbcManager;
import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;

/**
 * 读写分离： ds_master    ds_slave0    ds_slave1   ds_slave2
 *
 * @author maoyz
 */
public class ShardingJdbcMasterSlaveYamlManager {

    public static void main(String[] args) throws Exception {

        // 创建数据源
        DataSource dataSource = YamlMasterSlaveDataSourceFactory.createDataSource(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "META-INF/master-slave.yml"));
        for (int i = 0; i < 10; i++) {
            System.out.println("\r\n");
            ShardingJdbcManager.executeQuerySql(dataSource);
        }

        // insert
        for (int i = 0; i < 5; i++) {
            int i1 = ShardingJdbcManager.executeInsertSql(dataSource);
            System.out.println(i1);
        }
    }

}