/**
 * Copyright 2019 Inc.
 **/
package com.myz.redis.limit.service.limit;

/**
 * 限制规则
 *
 * @author maoyz0621 on 19-8-25
 * @version: v1.0
 */
public class LimitRule {
    /**
     * 单位时间
     */
    private int seconds;

    /**
     * 限制次数
     */
    private int limitCount;

    /**
     * 锁次数
     */
    private int lockCount;

    /**
     * 锁时长
     */
    private int lockTime;

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(int limitCount) {
        this.limitCount = limitCount;
    }

    public int getLockCount() {
        return lockCount;
    }

    public void setLockCount(int lockCount) {
        this.lockCount = lockCount;
    }

    public int getLockTime() {
        return lockTime;
    }

    public void setLockTime(int lockTime) {
        this.lockTime = lockTime;
    }

    public boolean enableLockLimit() {
        return getLockCount() > 0 && getLockTime() > 0;
    }

}
