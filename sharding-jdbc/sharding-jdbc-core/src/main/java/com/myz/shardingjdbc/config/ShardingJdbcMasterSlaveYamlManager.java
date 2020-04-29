package com.myz.shardingjdbc.config;

import org.apache.shardingsphere.shardingjdbc.api.yaml.YamlMasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * yml文件配置
 *
 * @author maoyz
 */
public class ShardingJdbcMasterSlaveYamlManager
        extends ShardingJdbcBaseManager {

    public static void main(String[] args) throws SQLException, IOException {

        // 创建数据源
        DataSource dataSource = YamlMasterSlaveDataSourceFactory.createDataSource(new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "masterslave.yml"));
        executeSql(dataSource);
    }

}