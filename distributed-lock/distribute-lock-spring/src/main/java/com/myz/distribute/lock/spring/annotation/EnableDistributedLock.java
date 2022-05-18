/**
 * Copyright 2022 Inc.
 **/
package com.myz.distribute.lock.spring.annotation;

import com.myz.distributed.lock.core.config.DistributedLockAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启分布式锁功能 @Enable*
 * 使用 - @Import
 * 直接引入配置类
 * 实现ImportSelector接口的类
 * 实现ImportBeanDefinitionRegistrar接口的类
 *
 * @author maoyz0621 on 2022/5/13
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({DistributedLockRegistrar.class, DistributedLockAutoConfiguration.class})
public @interface EnableDistributedLock {
}