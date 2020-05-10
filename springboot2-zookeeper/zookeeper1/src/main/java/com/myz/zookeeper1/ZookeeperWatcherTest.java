/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper1;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author maoyz on 2018/9/3
 * @version: v1.0
 */
public class ZookeeperWatcherTest {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperWatcherTest.class);

    private static final String CONNECTING = "127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184";
    private static final Integer TIMEOUT = 360000;

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZookeeperWatcher watcher = new ZookeeperWatcher(CONNECTING, TIMEOUT);

        if (null == watcher.getZooKeeper()) {
            logger.error("zk初始化失败");
            return;
        }

        ZooKeeper zooKeeper = watcher.getZooKeeper();
        // 监控节点创建
        if (null != zooKeeper.exists("/watcher", true)) {
            zooKeeper.delete("/watcher", -1);
        }
        // 设置watcher监听
        if (null == zooKeeper.exists("/watcher", true)) {
            zooKeeper.create("/watcher", "/watcher".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }

        // 设置watcher监听
        if (null != zooKeeper.exists("/watcher", true)) {
            zooKeeper.setData("/watcher", "1111".getBytes(), -1);
        }

    }
}
