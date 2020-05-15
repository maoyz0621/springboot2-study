/**
 * Copyright 2019 Inc.
 **/
package com.myz.redisson.service.redisson;

import com.myz.redisson.config.FastJsonCodec;
import com.myz.redisson.utils.SpringContextHolder;
import org.redisson.api.*;
import org.redisson.client.RedisTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author maoyz0621 on 19-7-31
 * @version: v1.0
 */
@Service
public class RedissonServiceImpl implements RedissonService {

    private static final Logger logger = LoggerFactory.getLogger(RedissonServiceImpl.class);

    @Autowired
    RedissonClient redissonClient;

    @Override
    public <V> RBucket<V> getRBucket(String objectName, V... vs) {
        return redissonClient.getBucket(objectName, SpringContextHolder.getBean(FastJsonCodec.class));
    }

    @Override
    public <K, V> RMap<K, V> getRMap(String objectName, V... vs) {
        return null;
    }

    @Override
    public RLock getRLock(String objectName) {
        return null;
    }

    @Override
    public <T> T lock(String objectName, CallBackService<T> callBackService) {
        return this.lock(objectName, 5, 5, callBackService);
    }

    @Override
    public <T> T lock(String objectName, long waitTime, long leaseTime, CallBackService<T> callBackService) {
        RLock lock = this.getRLock(objectName);
        try {
            lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
            return callBackService.exec();
        } catch (InterruptedException e) {
            logger.error("redisson lock error: {}", e);
        } catch (RedisTimeoutException e) {
            logger.error("redisson connect timeout: {}", e);
            return callBackService.exec();
        } catch (Exception e) {
            throw e;
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public RAtomicLong getRAtomicLong(String objectName) {
        return null;
    }

    @Override
    public <V> RQueue<V> getRQueue(String objectName) {
        return null;
    }

    @Override
    public <V> RDeque<V> getRDeque(String objectName) {
        return null;
    }

    @Override
    public RCountDownLatch getRCountDownLatch(String objectName) {
        return null;
    }

    @Override
    public RTopic getRTopic(String objectName) {
        return null;
    }

    @Override
    public <V> RList<V> getRList(String objectName) {
        return null;
    }

    @Override
    public <V> RSet<V> getRSet(String objectName) {
        return null;
    }
}
