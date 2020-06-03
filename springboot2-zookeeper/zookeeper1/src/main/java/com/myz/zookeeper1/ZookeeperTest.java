/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper1;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 基于原生API方式
 *
 * @author maoyz on 2018/4/25
 * @Version: v1.0
 */
public class ZookeeperTest {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperTest.class);

    private static final String CONNECTING = "172.19.0.1";

    private static final Integer TIMEOUT = 3600;

    // 计数器
    private final static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(CONNECTING, TIMEOUT, new Watcher() {

            // 事件通知
            @Override
            public void process(WatchedEvent watchedEvent) {
                // 事件状态
                Event.KeeperState status = watchedEvent.getState();
                if (Event.KeeperState.SyncConnected == status) {
                    // 事件类型
                    Event.EventType type = watchedEvent.getType();
                     /*
                      None (-1),
                      NodeCreated (1),
                      NodeDeleted (2),
                      NodeDataChanged (3),
                      NodeChildrenChanged (4);*/
                    if (Event.EventType.None == type) {
                        countDownLatch.countDown();
                        logger.debug("-------zookeeper start---------");
                    }
                } else {
                    logger.debug("----collect false------");
                }
            }
        });

        // 确保服务器连接后执行创建
        countDownLatch.await();
        // 创建节点create(final String path, byte data[], List<ACL> acl, CreateMode createMode),重复创建会抛出异常,父路径必须存在,节点类型只支持byte[]
        /*PERSISTENT (0, false, false),     持久
          PERSISTENT_SEQUENTIAL (2, false, true),   持久、有序
          EPHEMERAL (1, true, false),       临时
          EPHEMERAL_SEQUENTIAL (3, true, true);    临时、有序
        */
        // if (zooKeeper.exists("/test_3")){}

        // 1 临时节点
        zooKeeper.create("/test_1", "aaa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        // 临时节点不允许有子节点，会抛出异常
        // zooKeeper.create("/test_1/tt1", "aaa".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        // 2 持久节点目录
        zooKeeper.create("/test_t1", "t1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        // 持久节点子目录
        zooKeeper.create("/test_t1/tt1", "tt1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);


        List<String> children = zooKeeper.getChildren("/test_t1", true);
        logger.debug("/test_t1下所有子节点:" + children.toString());

        byte[] data = zooKeeper.getData("/test_t1", true, null);
        logger.debug("/test_t1节点内容:" + new String(data));

        logger.warn("=========== 执行删除操作 ==============");

       /* 只能删除子节点
         rc表示return code，就是返回码，0即为正常。
         path是传入API的参数，ctx也是传入的参数。
         注意在删除过程中，是需要版本检查的，所以我们一般提供-1跳过版本检查机制。
       */
        zooKeeper.delete("/test_t1/tt1", -1, (rc, path, ctx) -> {
            // 0
            logger.debug("rc:" + rc);
            // /test_t1/tt1
            logger.debug("path:" + path);
            // 传入的值aa
            logger.debug("ctx:" + ctx);
        }, "aa");

        // 关闭服务器
        zooKeeper.close();
    }

}
