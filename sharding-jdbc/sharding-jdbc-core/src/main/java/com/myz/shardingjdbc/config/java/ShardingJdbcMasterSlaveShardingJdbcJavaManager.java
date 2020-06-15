package com.myz.shardingjdbc.config.java;

import com.myz.shardingjdbc.api.ShardingJdbcManager;
import com.myz.shardingjdbc.config.ShardingJdbcJavaManager;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * java 编程式 分库分表和读写分离
 *
 * @author maoyz
 */
public class ShardingJdbcMasterSlaveShardingJdbcJavaManager extends ShardingJdbcJavaManager {

    public static void main(String[] args) throws SQLException {
        Map<String, DataSource> dataSourceMap = masterSlaveShardingJdbcDataSourceMap();

        // sql.show (?)	boolean	是否打印SQL解析和改写日志，默认值: false
        // executor.size (?)	int	用于SQL执行的工作线程数量，为零则表示无限制。默认值: 0
        // max.connections.size.per.query (?)	int	每个物理数据库为每次查询分配的最大连接数量。默认值: 1
        // check.table.metadata.enabled (?)	boolean	是否在启动时检查分表元数据一致性，默认值: false
        // MasterSlaveLoadBalanceAlgorithm 对应type

        // 配置读写分离规格
        ShardingRuleConfiguration configuration = masterSlaveShardingJdbcRuleConfiguration();

        // 创建数据源
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, configuration, new Properties());

        ShardingJdbcManager.executeQuerySql(dataSource);
    }

}
