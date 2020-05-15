/**
 * Copyright 2019 Inc.
 **/
package com.myz.redisson.service.redisson;

import org.redisson.api.*;

/**
 * @author maoyz0621 on 19-7-31
 * @version: v1.0
 */
public interface RedissonService {

    /**
     * 获取字符串对象
     *
     * @param vs         不需要传的值用来区分泛型类型
     * @param objectName
     * @return
     */
    <V> RBucket<V> getRBucket(String objectName, V... vs);

    /**
     * 获取Map对象
     *
     * @param objectName
     * @return
     */
    <K, V> RMap<K, V> getRMap(String objectName, V... vs);

    /**
     * 获取锁
     *
     * @param objectName
     * @return
     */
    RLock getRLock(String objectName);

    /**
     * lock
     *
     * @param objectName
     * @return
     * @throws Exception
     */
    <T> T lock(String objectName, CallBackService<T> callBackService);

    /**
     * lock
     *
     * @param objectName
     * @return
     * @throws InterruptedException
     */
    <T> T lock(String objectName, long waitTime, long leaseTime, CallBackService<T> callBackService);

    /**
     * 获取原子数
     *
     * @param objectName
     * @return
     */
    RAtomicLong getRAtomicLong(String objectName);

    /**
     * 获取队列
     *
     * @param objectName
     * @return
     */
    <V> RQueue<V> getRQueue(String objectName);

    /**
     * 获取双端队列
     *
     * @param objectName
     * @return
     */
    <V> RDeque<V> getRDeque(String objectName);

    /**
     * 获取记数锁
     *
     * @param objectName
     * @return
     */
    RCountDownLatch getRCountDownLatch(String objectName);

    /**
     * 获取消息的Topic
     *
     * @param objectName
     * @return
     */
    RTopic getRTopic(String objectName);

    /**
     * 获取列表
     *
     * @param objectName
     * @return
     */
    <V> RList<V> getRList(String objectName);

    /**
     * 获取集合
     *
     * @param objectName
     * @return
     */
    <V> RSet<V> getRSet(String objectName);
}
