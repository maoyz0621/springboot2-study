/**
 * Copyright 2019 Inc.
 **/
package com.myz.redis.limit.service.limit;

/**
 * @author maoyz0621 on 19-8-25
 * @version: v1.0
 */
public class AccessSpeedLimit {

    /**
     * 针对资源key,每seconds秒最多访问maxCount次,超过maxCount次返回false
     *
     * @param key        锁key
     * @param seconds    时间单位
     * @param limitCount 限制次数
     * @return boolean 是否执行
     */
    public boolean tryAccess(String key, int seconds, int limitCount) {
        LimitRule limitRule = new LimitRule();
        limitRule.setSeconds(seconds);
        limitRule.setLimitCount(limitCount);
        return tryAccess(key, limitRule);
    }

    /**
     * 针对资源key,每limitRule.seconds秒最多访问limitRule.limitCount,超过limitCount次返回false
     * 超过lockCount 锁定lockTime
     *
     * @param key
     * @param limitRule
     * @return
     */
    public boolean tryAccess(String key, LimitRule limitRule) {
        return true;
    }
}
