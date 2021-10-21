/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.log.annotation;

import com.myz.log.aop.common.log.enums.LogEventType;
import com.myz.log.aop.common.log.enums.LogModuleType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 这里定义日志的详细内容。如果此注解注解在类上，则这个参数做为类全部方法的默认值。如果注解在方法上，则只对这个方法启作用
 *
 * @author maoyz0621 on 2018/12/28
 * @version: v1.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogEvent {

    /**
     * 日志所属的模块
     */
    LogModuleType module() default LogModuleType.DEFAULT;

    /**
     * 日志事件类型
     */
    LogEventType event() default LogEventType.DEFAULT;

    /**
     * 描述信息
     */
    String desc() default "";

}
