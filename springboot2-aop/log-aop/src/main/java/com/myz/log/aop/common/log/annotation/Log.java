/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.log.annotation;

import java.lang.annotation.*;

/**
 * @author maoyz0621 on 2018/12/27
 * @version: v1.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Log {

    String value() default "";

    /**
     * 是否记录传递参数
     */
    boolean param() default false;

    /**
     * 记录执行结果
     */
    boolean result() default false;

    /**
     * 如果为true，则类下面的LogEvent启作用，否则忽略
     */
    boolean logEnable() default true;

    /**
     * 是否异步执行,默认为true
     *
     * @return true, 如果需要异步执行
     */
    boolean async() default true;
}
