/**
 * Copyright 2021 Inc.
 **/
package com.myz.config.enable.monitor;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

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