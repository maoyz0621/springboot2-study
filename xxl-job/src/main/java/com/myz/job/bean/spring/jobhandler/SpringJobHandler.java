/**
 * Copyright 2020 Inc.
 **/
package com.myz.job.bean.spring.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import com.xxl.job.core.util.ShardingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 20-4-26
 * @version v1.0
 */
@Component
public class SpringJobHandler {
    private static Logger log = LoggerFactory.getLogger(SpringJobHandler.class);

    @XxlJob("firstJobHandler")
    public ReturnT<String> firstJobHandler(String s) throws Exception {
        XxlJobLogger.log("xxl-job, SpringJobHandler");
        for (int i = 0; i < 5; i++) {
            log.info("============ SpringJobHandler firstJobHandler ================");
            XxlJobLogger.log(" start :" + i);
            // 模拟业务用时
            TimeUnit.SECONDS.sleep(3);
        }
        return ReturnT.SUCCESS;
    }

    /**
     * 分片广播任务
     * 执行器集群部署，任务路由选择"分片广播"
     * 该特性适用场景如：
     * 1、分片任务场景：10个执行器的集群来处理10w条数据，每台机器只需要处理1w条数据，耗时降低10倍；
     * 2、广播任务场景：广播执行器机器运行shell脚本、广播集群节点进行缓存更新等
     */
    @XxlJob("shardingJobHandler")
    public ReturnT<String> shardingJobHandler(String s) throws Exception {
        ShardingUtil.ShardingVO shardingVO = ShardingUtil.getShardingVo();
        XxlJobLogger.log("xxl job shardingJobHandler,index={},total={}", shardingVO.getIndex(), shardingVO.getTotal());
        log.info("============ xxl job shardingJobHandler,index={},total={} ================", shardingVO.getIndex(), shardingVO.getTotal());

        for (int i = 0; i < shardingVO.getTotal(); i++) {
            if (i == shardingVO.getIndex()) {
                XxlJobLogger.log("第{}片开始处理", i);
                log.info("第{}片开始处理", i);
            } else {
                XxlJobLogger.log("第{}片忽略", i);
                log.info("第{}片忽略", i);
            }
        }

        return ReturnT.SUCCESS;
    }
}
