/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper1.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于zk实现的分布式锁
 *
 * @author maoyz on 2018/9/3
 * @version: v1.0
 */
public class ZookeeperLock implements Lock, Watcher {

    private final Logger logger = LoggerFactory.getLogger(ZookeeperLock.class);

    private ZooKeeper zooKeeper = null;
    /**
     * 根节点
     */
    private static final String ROOT_LOCK = "/locks";
    /**
     * 竞争的资源
     */
    private String lockName;
    /**
     * 等待的前一个锁
     */
    private String waitLock;
    /**
     * 当前锁
     */
    private String currentLock;
    /**
     * 计数器
     */
    private CountDownLatch countDownLatch;

    private int sessionTimeout = 30000;

    private CountDownLatch connectedSignal = new CountDownLatch(1);

    private List<Exception> exceptionList = new ArrayList<>();

    public ZookeeperLock(String config, String lockName) {
        this.lockName = lockName;

        try {
            zooKeeper = new ZooKeeper(config, sessionTimeout, this);
            connectedSignal.await();

            if (null == zooKeeper.exists(ROOT_LOCK, false)) {
                // 如果根节点不存在，则创建根节点
                zooKeeper.create(ROOT_LOCK, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException | KeeperException | InterruptedException e) {
            logger.error("buildFailure  = {}", e.getStackTrace());
        }
    }

    @Override
    public void lock() {
        if (exceptionList.size() > 0) {
            throw new LockException(exceptionList.get(0));
        }

        try {
            if (this.tryLock()) {
                logger.info(lockName + ":获得了锁");
                logger.debug(lockName + ":获得了锁");
                return;
            } else {
                waitForLock(waitLock, sessionTimeout);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 等待锁
     */
    private boolean waitForLock(String prev, int sessionTimeout) {
        try {
            // 同时注册监听
            Stat stat = zooKeeper.exists(ROOT_LOCK + "/" + prev, true);

            // 判断比自己小一个数的节点是否存在,如果不存在则无需等待锁,同时注册监听
            if (null != stat) {
                this.countDownLatch = new CountDownLatch(1);
                logger.debug(Thread.currentThread().getName() + "等待锁 " + ROOT_LOCK + "/" + prev);

                // 等待，这里应该一直等待其他线程释放锁
                this.countDownLatch.await(sessionTimeout, TimeUnit.MILLISECONDS);
                this.countDownLatch = null;
                logger.debug(Thread.currentThread().getName() + " 等到了锁");
            }
        } catch (KeeperException | InterruptedException e) {
            logger.error(e.getMessage());
        }
        return true;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.lock();
    }

    /**
     * 上锁
     */
    @Override
    public boolean tryLock() {
        try {
            String splitStr = "_lock_";
            // 创建临时有序节点
            this.currentLock = zooKeeper.create(ROOT_LOCK + "/" + lockName + splitStr, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            logger.debug("当前锁:" + currentLock + " 已经创建");

            // 取所有子节点,无需监控
            final List<String> subNodes = zooKeeper.getChildren(ROOT_LOCK, false);
            if (subNodes.size() == 1) {
                if (subNodes.get(0).equals(this.currentLock.substring(this.currentLock.lastIndexOf("/") + 1))) {
                    return true;
                }
            }

            // 取出所有lockName的锁
            List<String> lockObjects = new ArrayList<>();
            for (String subNode : subNodes) {
                // lockName
                String node = subNode.split(splitStr)[0];
                // 确保传入名称相同
                if (Objects.equals(node, lockName)) {
                    lockObjects.add(subNode);
                }
            }

            // 排序
            Collections.sort(lockObjects);
            logger.debug("此时取所有子节点:" + lockObjects.toString());

            logger.info(Thread.currentThread().getName() + " 的锁是 " + currentLock);

            // 若当前节点为最小节点，则获取锁成功
            if (Objects.equals(currentLock, ROOT_LOCK + lockObjects.get(0))) {
                return true;
            }

            // 若不是最小节点，则找到currentLock的前一个节点
            String prevNode = currentLock.substring(currentLock.lastIndexOf("/") + 1);
            // 成为等待锁
            waitLock = lockObjects.get(Collections.binarySearch(lockObjects, prevNode) - 1);
            logger.debug("前一个节点:" + waitLock);

        } catch (KeeperException e) {
            logger.error(e.getMessage());
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        try {
            if (this.tryLock()) {
                return true;
            }
            return waitForLock(waitLock, sessionTimeout);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * 释放锁
     */
    @Override
    public void unlock() {
        try {
            logger.info("释放锁 " + currentLock);

            zooKeeper.delete(currentLock, -1);
            currentLock = null;
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**
     * zookeeper节点的监视器
     */
    @Override
    public void process(WatchedEvent event) {
        // 连接状态
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType()) {
                if (null != connectedSignal) {
                    connectedSignal.countDown();
                    logger.debug("建立连接 ....");
                    return;
                }
            } else if (Event.EventType.NodeDeleted == event.getType()) {
                // 其他线程放弃锁的标志
                if (this.countDownLatch != null) {
                    this.countDownLatch.countDown();
                }
            }
        }
    }

    public class LockException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public LockException(String e) {
            super(e);
        }

        public LockException(Exception e) {
            super(e);
        }
    }
}
