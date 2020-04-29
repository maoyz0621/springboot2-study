package com.myz.shardingjdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.masterslave.LoadBalanceStrategyConfiguration;

import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * java 编程式
 * @author maoyz
 */
public class ShardingJdbcMasterSlaveJavaManager
        extends ShardingJdbcBaseManager {

    public static void main(String[] args) throws SQLException {
        Map<String, DataSource> dataSourceMap = dataSourceMap();

        // sql.show (?)	boolean	是否打印SQL解析和改写日志，默认值: false
        // executor.size (?)	int	用于SQL执行的工作线程数量，为零则表示无限制。默认值: 0
        // max.connections.size.per.query (?)	int	每个物理数据库为每次查询分配的最大连接数量。默认值: 1
        // check.table.metadata.enabled (?)	boolean	是否在启动时检查分表元数据一致性，默认值: false
        // MasterSlaveLoadBalanceAlgorithm 对应type

        Properties properties = new Properties();
        properties.setProperty("sql.show", "true");
        properties.setProperty("executor.size", "10");
        // 配置读写分离规格
        MasterSlaveRuleConfiguration masterSlaveRuleConfiguration = new MasterSlaveRuleConfiguration("ds_master_slave",
                "ds_master",
                Arrays.asList("ds_slave0", "ds_slave1", "ds_slave2"),
                // 从库负载均衡算法
                new LoadBalanceStrategyConfiguration("RANDOM", properties));

        // 创建数据源
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfiguration, new Properties());

        executeSql(dataSource);
    }

    private static Map<String, DataSource> dataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 数据源
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl(String.format(url, "ds_master"));
        dataSource1.setUsername("root");
        dataSource1.setPassword("root");
        dataSourceMap.put("ds_master", dataSource1);

        DruidDataSource dataSource2 = new DruidDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setUrl(String.format(url, "ds_slave0"));
        dataSource2.setUsername("root");
        dataSource2.setPassword("root");
        dataSourceMap.put("ds_slave0", dataSource2);

        DruidDataSource dataSource3 = new DruidDataSource();
        dataSource3.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource3.setUrl(String.format(url, "ds_slave1"));
        dataSource3.setUsername("root");
        dataSource3.setPassword("root");
        dataSourceMap.put("ds_slave1", dataSource3);

        DruidDataSource dataSource4 = new DruidDataSource();
        dataSource4.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource4.setUrl(String.format(url, "ds_slave2"));
        dataSource4.setUsername("root");
        dataSource4.setPassword("root");
        dataSourceMap.put("ds_slave2", dataSource4);
        return dataSourceMap;
    }

}
