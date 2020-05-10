/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper2.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * ACL权限：
 *
 * @author maoyz on 2018/9/4
 * @version: v1.0
 */
public class CuratorTest3 {

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

        //ACL有IP授权和用户名密码访问的模式
        ACL aclRoot = new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("root:root")));
        List<ACL> aclList = new ArrayList<ACL>();
        aclList.add(aclRoot);

        String path = curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL)
                .withACL(aclList)
                .forPath("/node_3/node_ACL", "2".getBytes());
        System.out.println(path);

        CuratorFramework client1 = CuratorFrameworkFactory.builder()
                .connectString("192.168.0.3:2181")
                .sessionTimeoutMs(5000)//会话超时时间
                .connectionTimeoutMs(5000)//连接超时时间
                .authorization("digest", "root:root".getBytes())//权限访问
                .retryPolicy(retryPolicy)
                .build();

        client1.start();

        String re = new String(client1.getData().forPath("/node_3/node_ACL"));
        System.out.println(re);
    }
}
