package com.myz.shardingjdbc.config.java;

import com.myz.shardingjdbc.api.ShardingJdbcManager;
import com.myz.shardingjdbc.config.ShardingJdbcJavaManager;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * 分库分表和读写分离
 * demo_ds_master_0   demo_ds_master_0_slave_0   demo_ds_master_0_slave_1   demo_ds_master_1   demo_ds_master_1_slave_0   demo_ds_master_1_slave_1
 * 表 t_order
 *
 * @author maoyz
 */
public class ShardingJdbcMasterSlaveShardingDbTableJavaManager extends ShardingJdbcJavaManager {

    public static void main(String[] args) throws Exception {
        Map<String, DataSource> dataSourceMap = masterSlaveShardingDbTableDataSourceMap();

        // sql.show (?)	boolean	是否打印SQL解析和改写日志，默认值: false
        // executor.size (?)	int	用于SQL执行的工作线程数量，为零则表示无限制。默认值: 0
        // max.connections.size.per.query (?)	int	每个物理数据库为每次查询分配的最大连接数量。默认值: 1
        // check.table.metadata.enabled (?)	boolean	是否在启动时检查分表元数据一致性，默认值: false
        // MasterSlaveLoadBalanceAlgorithm 对应type

        // 配置分库分表、读写分离规格
        ShardingRuleConfiguration configuration = masterSlaveShardingDbTableRuleConfiguration();

        // 创建数据源
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, configuration, prop());

        for (int i = 0; i < 10; i++) {
            int i1 = ShardingJdbcManager.executeInsertSqlNoId(dataSource);
            System.out.println(i1);
        }

        ShardingJdbcManager.executeQuerySqlShardingDb(dataSource);
    }

}
