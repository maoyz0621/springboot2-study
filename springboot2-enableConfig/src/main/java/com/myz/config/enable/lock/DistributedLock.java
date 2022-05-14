/**
 * Copyright 2022 Inc.
 **/
package com.myz.config.enable.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    String name() default "";

    long acquireTimeout() default -1;

    boolean autoRelease() default true;
}