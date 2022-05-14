/**
 * Copyright 2021 Inc.
 **/
package com.myz.config.enable.monitor.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 服务器负载监控开启注解
 *
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ServerLoadMonitorAutoConfig.class)
public @interface EnableServerLoadMonitor {
}