/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-17 21:38  Inc. All rights reserved.
 */
package com.myz.redis.cache.core.annotaion;

import com.myz.redis.cache.core.LockType;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RedisLockable {
    String key() default "";

    LockType type() default LockType.NOWAIT;

    long expire() default 2000L;

    int tryCount() default 10;

    long sleep() default 100L;

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
