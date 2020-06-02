http://shardingsphere.apache.org/index_zh.html


[Err] 1055 - Expression #1 of ORDER BY clause is not in GROUP BY clause and contains nonaggregated column 'information_schema.PROFILING.SEQ' which is not functionally dependent on columns in GROUP BY clause; this is incompatible with sql_mode=only_full_group_by

SET sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));

概念：

逻辑表: 不存在的表总称 t_order

真实表: 真实存在的物理表 t_order_1 t_order_2

数据节点: 数据源名称 + 真实表 ds_0.T_order_0

数据源分片

表分片

分片算法：
    
    1) 精确分片算法  对应 PreciseShardingAlgorithm，用于处理使用单一键作为分片键的 = 与 IN 进行分片的场景。需要配合 StandardShardingStrategy 使用。
    2) 范围分片算法  对应 RangeShardingAlgorithm，用于处理使用单一键作为分片键的 BETWEEN AND、>、<、>=、<=进行分片的场景。需要配合 StandardShardingStrategy 使用
    3) 复合分片算法  对应 ComplexKeysShardingAlgorithm，用于处理使用多键作为分片键进行分片的场景，包含多个分片键的逻辑较复杂，需要应用开发者自行处理其中的复杂度。需要配合 ComplexShardingStrategy 使用
    4) Hint分片算法 对应 HintShardingAlgorithm，用于处理使用 Hint 行分片的场景。需要配合 HintShardingStrategy 使用。
    
分片策略：

    1) 标准分片策略 StandardShardingStrategy。提供对 SQL语句中的 =, >, <, >=, <=, IN 和 BETWEEN AND 的分片操作支持
    2) 复合分片策略 ComplexShardingStrategy。复合分片策略。提供对 SQL 语句中的 =, >, <, >=, <=, IN 和 BETWEEN AND 的分片操作支持
    3) 行表达式分片策略 InlineShardingStrategy。使用 Groovy 的表达式，提供对 SQL 语句中的 = 和 IN 的分片操作支持，只支持单分片键。 对于简单的分片算法，可以通过简单的配置使用，从而避免繁琐的Java代码开发，如: t_user_$->{u_id % 8} 表示 t_user 表根据 u_id 模 8，而分成 8 张表，表名称为 t_user_0 到 t_user_7
    4) Hint分片策略 HintShardingStrategy。通过 Hint 指定分片值而非从 SQL 中提取分片值的方式进行分片的策略。
    5) 不分片策略  NoneShardingStrategy

行表达式：
    ${begin..end}  范围区间
    
    ${[unit1, unit2, unit_x]} 表示枚举值
    
    笛卡尔组合 ${['online', 'offline']}_table${1..3} 
        online_table1, online_table2, online_table3, offline_table1, offline_table2, offline_table3
        
    案例：$  等同于 $->
        db0
          ├── t_order0 
          └── t_order1 
        db1
          ├── t_order0 
          └── t_order1
        
        db${0..1}.t_order${0..1}
        或
        db$->{0..1}.t_order$->{0..1}
        
分片算法:
    ds${id % 10}
    或
    ds$->{id % 10}
    
    
归并：
    便利归并：单向链表  
    排序归并：Comparable 优先级队列  
    分组归并：流式分组归并和内存分组归并  
    聚合归并：  
    分页归并：
    
存在场景：
    1） 单库分表
    2） 分库
    3） 分库分表
    4） 读写分离
    4.1） 一主多从
    4.2） 多主多从
    
    
源码解析：https://mp.weixin.qq.com/s?__biz=MzUzMTA2NTU2Ng==&mid=2247484360&idx=1&sn=0dae84944d2c388fdc1bbed868ac5b99&chksm=fa497c79cd3ef56f8487dda6d53e3772e0aa9812ee66376993c3445bc94920c01a03dd4a4b8f&scene=21#wechat_redirect