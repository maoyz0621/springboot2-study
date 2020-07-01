/**
 * 分库：*ShardingDb*               demo_ds_0    demo_ds_1
 * 分表：*ShardingTable*            demo_ds.t_order
 * 分库分表：*ShardingDbTable*       demo_ds_0.t_order   demo_ds_1.t_order
 * 读写分离：*MasterSlave*           ds_master    ds_slave0    ds_slave1   ds_slave2
 * 分库分表、读写分离：*MasterSlaveShardingDbTable*       demo_ds_master_0   demo_ds_master_0_slave_0   demo_ds_master_0_slave_1   demo_ds_master_1   demo_ds_master_1_slave_0   demo_ds_master_1_slave_1  /  t_order
 *
 * @author maoyz
 */
package com.myz.shardingjdbc.config;