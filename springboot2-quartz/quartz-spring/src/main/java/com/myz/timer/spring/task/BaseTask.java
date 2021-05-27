/**
 * Copyright 2019 Inc.
 **/
package com.myz.timer.spring.task;

import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;

/**
 * @author maoyz0621 on 19-5-19
 * @version: v1.0
 */
public class BaseTask {

    @Autowired
    protected ExecutorService executorService;

    /**
     * 使用ThreadLocal实现函数式编程
     */
    protected static final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));

}
