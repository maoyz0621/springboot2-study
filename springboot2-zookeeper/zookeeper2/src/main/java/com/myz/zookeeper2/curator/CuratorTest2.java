/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper2.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 子节点监听PathChildrenCacheListener
 *
 * @author maoyz on 2018/9/4
 * @version: v1.0
 */
public class CuratorTest2 {

    public static void main(String[] args) throws Exception {
        // 创建zookeeper的客户端 ,重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);

        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1：2181")
                .sessionTimeoutMs(100000)
                .retryPolicy(retryPolicy)
                .build();

        //3 开启连接
        curatorFramework.start();


        final PathChildrenCache cache = new PathChildrenCache(curatorFramework, "/children_node", true);
        /**
         * 如果不填写这个参数，则无法监听到子节点的数据更新
         如果参数为PathChildrenCache.StartMode.BUILD_INITIAL_CACHE，则会预先创建之前指定的/super节点
         如果参数为PathChildrenCache.StartMode.POST_INITIALIZED_EVENT，效果与BUILD_INITIAL_CACHE相同，只是不会预先创建/super节点
         参数为PathChildrenCache.StartMode.NORMAL时，与不填写参数是同样的效果，不会监听子节点的数据更新操作
         */
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);


        // 监听
        cache.getListenable().addListener((CuratorFramework curator, PathChildrenCacheEvent event) -> {
            switch (event.getType()) {
                case CHILD_ADDED:
                    System.out.println("add:" + event.getData());
                    break;
                case CHILD_UPDATED:
                    System.out.println("update:" + event.getData());
                    break;
                case CHILD_REMOVED:
                    System.out.println("remove:" + event.getData());
                    break;
                default:
                    break;
            }
        });


    }
}
