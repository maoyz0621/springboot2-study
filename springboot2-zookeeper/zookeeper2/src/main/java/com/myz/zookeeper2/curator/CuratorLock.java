/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper2.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 基于Curator实现的分布式锁
 *
 * @author maoyz on 2018/9/4
 * @version: v1.0
 */
public class CuratorLock {

    public static void main(String[] args) {
        // 创建zookeeper的客户端
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(100000)
                .retryPolicy(retryPolicy)
                .build();

        // 启动服务
        client.start();

        // 创建分布式锁, 锁空间的根节点路径为/curator/lock
        InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");

        try {
            // 获取锁
            mutex.acquire();

            // todo 执行业务
            System.out.println("执行业务");

            // 完成业务流程, 释放锁
            mutex.release();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭客户端
            client.close();
        }
    }
}
