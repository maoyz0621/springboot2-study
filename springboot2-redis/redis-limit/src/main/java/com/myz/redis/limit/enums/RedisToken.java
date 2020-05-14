/**
 * Copyright 2019 Inc.
 **/
package com.myz.redis.limit.enums;

/**
 * @author maoyz0621 on 19-4-14
 * @version: v1.0
 */
public enum RedisToken {

    SUCCESS, FAILED;

    public boolean isSuccess() {
        return this.equals(SUCCESS);
    }

    public boolean isFailed() {
        return this.equals(FAILED);
    }
}
