/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper1;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author maoyz on 2018/9/3
 * @version: v1.0
 */
public class ZookeeperWatcher implements Watcher {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * watch计数器
     */
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    /**
     * zk连接计数器
     */
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    private ZooKeeper zooKeeper = null;

    public ZookeeperWatcher(String connectString, int sessionTimeout) {
        try {
            zooKeeper = new ZooKeeper(connectString, sessionTimeout, this);

            countDownLatch.await();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    /**
     * Watch事件
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        // 类型
        Event.EventType type = event.getType();
        // 状态
        Event.KeeperState state = event.getState();
        // 路径
        String path = event.getPath();

        // 连接状态
        if (Event.KeeperState.SyncConnected == state) {
            if (Event.EventType.None == type) {
                countDownLatch.countDown();
                logger.debug("建立连接 ....");
            } else if (Event.EventType.NodeCreated == type) {
                logger.debug("节点创建:" + atomicInteger.incrementAndGet() + "====>" + path);
            } else if (Event.EventType.NodeDeleted == type) {
                logger.debug("节点删除:" + atomicInteger.incrementAndGet() + "====>" + path);
            } else if (Event.EventType.NodeDataChanged == type) {
                logger.debug("节点变化:" + atomicInteger.incrementAndGet() + "====>" + path);
            } else if (Event.EventType.NodeChildrenChanged == type) {
                logger.debug("子节点变化:" + atomicInteger.incrementAndGet() + "====>" + path);
            }
        }
    }
}
