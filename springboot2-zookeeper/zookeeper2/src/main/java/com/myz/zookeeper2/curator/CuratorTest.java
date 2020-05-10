/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper2.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * curator基本使用
 *
 * @author maoyz on 2018/9/4
 * @version: v1.0
 */
public class CuratorTest {

    private static final String CONNECTING = "127.0.0.1:21811,127.0.0.1:21812,127.0.0.1:21813";
    private static final Integer TIMEOUT = 36000;


    public static void main(String[] args) throws Exception {
        // 创建zookeeper的客户端 , 重试策略：初试时间为1s 重试10次
        // ExponentialBackoffRetry:重试指定的次数, 且每一次重试之间停顿的时间逐渐增加.
        // RetryNTimes:指定最大重试次数的重试策略
        // RetryOneTime:仅重试一次
        // RetryUntilElapsed:一直重试直到达到规定的时间
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);

        // connectString   服务器列表，格式host1:port1,host2:port2,...
        // retryPolicy   重试策略,内建有四种重试策略,也可以自行实现RetryPolicy接口
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(CONNECTING)
                .sessionTimeoutMs(TIMEOUT)
                .retryPolicy(retryPolicy)
                .namespace("maoyz")
                .build();

        //3 开启连接
        curatorFramework.start();

        // 4 创建节点 curatorFramework.create().forPath("path","init".getBytes());
        // 建立节点，若父节点不存在会先创建父节点再创建子节点, 指定节点类型（不加withMode默认为持久类型节点）、路径、数据内容,
        // inBackground()绑定回调函数，异步调用
        ExecutorService pool = Executors.newCachedThreadPool();
        curatorFramework.create()
                // 建立节点，若父节点不存在会先创建父节点再创建子节点
                .creatingParentsIfNeeded()
                // 指定节点类型
                .withMode(CreateMode.PERSISTENT)
                .inBackground((CuratorFramework client, CuratorEvent event) -> {
                    System.out.println("code:" + event.getResultCode());
                    System.out.println("type:" + event.getType());
                    System.out.println("线程为:" + Thread.currentThread().getName());
                }, pool)
                .forPath("/curator/c1", "curator-c1".getBytes());


        //为了能够看到回调信息
        Thread.sleep(2000);

        // 获取所有子节点  curatorFramework.getChildren().forPath("/curator")
        List<String> list = curatorFramework.getChildren().forPath("/curator");
        for (String s : list) {
            System.out.println("所有子节点:" + s);
        }

        // 只获取数据内容  curatorFramework.getData().forPath("path")
        String data = new String(curatorFramework.getData().forPath("/curator/c1"));
        System.out.println("/curator/c1 数据为 :" + data);


        // 在获取节点内容的同时把状态信息存入Stat对象   curatorFramework.getData().storingStatIn(stat1).forPath("/curator/c1"))
        Stat stat1 = new Stat();
        String re = new String(curatorFramework.getData().storingStatIn(stat1).forPath("/curator/c1"));
        System.out.println("state1==>" + stat1 + "re==>" + re);


        // 修改前获取一次节点数据得到版本信息  curatorFramework.setData().forPath("path","data".getBytes())
        curatorFramework.setData()
                // 更新一个节点的数据内容，强制指定版本进行更新
                .withVersion(stat1.getVersion())
                .forPath("/curator/c1", "111".getBytes());
        String newData = new String(curatorFramework.getData().forPath("/curator/c1"));
        System.out.println("/curator/c1修改后为 :" + newData);


        // 判断节点是否存在checkExists方法  curatorFramework.checkExists().forPath("/curator")
        Stat stat = curatorFramework.checkExists().forPath("/curator");
        System.out.println("节点 /curator :" + stat);

        // guaranteed()保障机制，若未删除成功，只要会话有效会在后台一直尝试删除,删除节点及子节点
        // client.delete().forPath("path") 注意，此方法只能删除叶子节点，否则会抛出异常。
        curatorFramework.delete()
                // 删除一个节点，强制保证删除
                .guaranteed()
                // 删除一个节点，并且递归删除其所有的子节点
                .deletingChildrenIfNeeded()
                // 删除一个节点，强制指定版本进行删除
                .withVersion(-1)
                .forPath("/curator");

        curatorFramework.close();
    }
}
