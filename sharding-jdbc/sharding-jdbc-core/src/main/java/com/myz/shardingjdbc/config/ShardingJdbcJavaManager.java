package com.myz.shardingjdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.myz.shardingjdbc.api.ShardingJdbcManager;
import org.apache.shardingsphere.api.config.masterslave.LoadBalanceStrategyConfiguration;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author maoyz
 */
public class ShardingJdbcJavaManager extends ShardingJdbcBaseManager {

    protected static Map<String, DataSource> shardingTableDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 数据源
        DruidDataSource dataSource1 = dataSource("demo_ds");
        dataSourceMap.put("ds", dataSource1);
        return dataSourceMap;
    }

    protected static Map<String, DataSource> shardingDbDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 数据源
        DruidDataSource dataSource1 = dataSource("demo_ds_0");
        DruidDataSource dataSource2 = dataSource("demo_ds_1");
        dataSourceMap.put("ds0", dataSource1);
        dataSourceMap.put("ds1", dataSource2);
        return dataSourceMap;
    }

    protected static Map<String, DataSource> masterSlaveDataSourceMap() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // 数据源
        DruidDataSource dataSource1 = dataSource("ds_master");
        DruidDataSource dataSource2 = dataSource("ds_slave0");
        DruidDataSource dataSource3 = dataSource("ds_slave1");
        DruidDataSource dataSource4 = dataSource("ds_slave2");

        dataSourceMap.put("ds_master", dataSource1);
        dataSourceMap.put("ds_slave0", dataSource2);
        dataSourceMap.put("ds_slave1", dataSource3);
        dataSourceMap.put("ds_slave2", dataSource4);
        return dataSourceMap;
    }

    protected static Properties prop() {
        Properties properties = new Properties();
        properties.setProperty("sql.show", "true");
        properties.setProperty("executor.size", "10");
        return properties;
    }

    protected static MasterSlaveRuleConfiguration masterSlaveRuleConfiguration(Properties properties) {
        return new MasterSlaveRuleConfiguration("ds_master_slave",
                "ds_master",
                Arrays.asList("ds_slave0", "ds_slave1", "ds_slave2"),
                // 从库负载均衡算法
                new LoadBalanceStrategyConfiguration("RANDOM", prop()));
    }

    protected static ShardingRuleConfiguration shardingRuleConfigurationDb() {
        // 表规则 魔法数字 $->{0..1}
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("t_order", "ds_${0..1}.t_order");

        // 分库策略
        // user_id取模分库
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds_${user_id % 2}"));

        // key生成
        tableRuleConfiguration.setKeyGeneratorConfig(getKeyGeneratorConfig());

        // 分片规则
        ShardingRuleConfiguration shardingRule = new ShardingRuleConfiguration();
        shardingRule.getTableRuleConfigs().add(tableRuleConfiguration);
        return shardingRule;
    }


    protected static ShardingRuleConfiguration shardingRuleConfigurationTable() {
        // 表规则 魔法数字 $->{0..1}
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("t_order", "ds.t_order_$->{0..1}");

        // 分表策略 order_id取模分表
        tableRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_$->{order_id % 2}"));

        // key生成
        tableRuleConfiguration.setKeyGeneratorConfig(getKeyGeneratorConfig());

        // 分片规则
        ShardingRuleConfiguration shardingRule = new ShardingRuleConfiguration();
        shardingRule.getTableRuleConfigs().add(tableRuleConfiguration);
        return shardingRule;
    }

    protected static ShardingRuleConfiguration shardingRuleConfigurationDbTable() {
        // 表规则 魔法数字 $->{0..1}
        TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("t_order", "ds$->{0..1}.t_order_$->{0..1}");

        // 分表策略 order_id取模分表
        tableRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order_$->{order_id % 2}"));
        // user_id取模分库
        tableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));

        // key生成
        tableRuleConfiguration.setKeyGeneratorConfig(getKeyGeneratorConfig());

        // 分片规则
        ShardingRuleConfiguration shardingRule = new ShardingRuleConfiguration();
        shardingRule.getTableRuleConfigs().add(tableRuleConfiguration);
        return shardingRule;
    }

    private static DruidDataSource dataSource(String urlPath) {
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setUrl(String.format(url, urlPath));
        dataSource1.setUsername("root");
        dataSource1.setPassword("root");
        return dataSource1;
    }

    private static KeyGeneratorConfiguration getKeyGeneratorConfig() {
        return new KeyGeneratorConfiguration("SNOWFLAKE", "order_id");
    }

}
