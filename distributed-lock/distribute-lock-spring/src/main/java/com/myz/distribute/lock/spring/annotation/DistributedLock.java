/**
 * Copyright 2022 Inc.
 **/
package com.myz.distribute.lock.spring.annotation;

import com.myz.distributed.lock.core.config.DistributedLockProperties;
import com.myz.distributed.lock.core.executor.DistributedLockExecutor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@Target(value = {ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DistributedLock {

    /**
     * 用于多个方法锁同一把锁 包名+类名+方法名
     *
     * @return 名称
     */
    String name() default "";

    /**
     * @return lock 执行器
     */
    Class<? extends DistributedLockExecutor> executor() default DistributedLockExecutor.class;

    /**
     * support SPEL expresion 锁的key = name + keys
     *
     * @return KEY
     */
    String[] keys() default "";

    /**
     * 过期时间一定是要长于业务的执行时间. 未设置则为默认时间30秒 默认值：{@link DistributedLockProperties#expire}
     *
     * @return 过期时间 单位：毫秒
     */
    long expire() default -1;

    /**
     * 结合业务,建议该时间不宜设置过长,特别在并发高的情况下. 未设置则为默认时间3秒 默认值：{@link DistributedLockProperties#acquireTimeout}
     *
     * @return 获取锁超时时间 单位：毫秒
     */
    long acquireTimeout() default -1;

    /**
     * 业务方法执行完后（方法内抛异常也算执行完）自动释放锁，如果为false，锁将不会自动释放直至到达过期时间才释放 {@link DistributedLock#expire()()}
     *
     * @return 是否自动释放锁
     */
    boolean autoRelease() default true;
}