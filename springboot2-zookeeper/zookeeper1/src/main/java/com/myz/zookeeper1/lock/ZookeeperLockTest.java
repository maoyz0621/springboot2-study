/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.zookeeper1.lock;

/**
 * @author maoyz on 2018/9/3
 * @version: v1.0
 */
public class ZookeeperLockTest {

    private static int n = 500;

    public static void secskill() {
        System.out.println(--n);
    }

    public static void main(String[] args) {

        Runnable runnable = () -> {
            ZookeeperLock lock = null;

            try {
                lock = new ZookeeperLock("127.0.0.1:2181", "test1");
                lock.lock();
                secskill();
                System.out.println(Thread.currentThread().getName() + "正在运行");
            } finally {
                if (lock != null) {
                    lock.unlock();
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }
}
