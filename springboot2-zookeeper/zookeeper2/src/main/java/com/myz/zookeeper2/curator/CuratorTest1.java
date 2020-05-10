/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper2.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 事件监听NodeCacheListener
 *
 * @author maoyz on 2018/9/4
 * @version: v1.0
 */
public class CuratorTest1 {

    public static void main(String[] args) throws Exception {
        // 创建zookeeper的客户端 ,重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(100000)
                .retryPolicy(retryPolicy)
                .namespace("maoyz")
                .build();

        //3 开启连接
        curatorFramework.start();

        final NodeCache nodeCache = new NodeCache(curatorFramework, "/node");
        nodeCache.start();

        nodeCache.getListenable().addListener(() -> {
            byte[] res = nodeCache.getCurrentData().getData();
            System.out.println("data: " + new String(res));
        });


    }
}
