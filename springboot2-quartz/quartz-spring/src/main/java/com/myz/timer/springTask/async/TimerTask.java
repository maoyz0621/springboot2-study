/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.springTask.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 定时任务
 *
 * @author maoyz on 2018/8/8
 * @version: v1.0
 */
@Component
public class TimerTask {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));

    /**
     * 基于spring实现的定时任务
     */
    @Scheduled(cron = "0/2 * * * * ?")
    public void schedule() {
        logger.debug("{} 执行定时任务 {}", Thread.currentThread().getName(), DATE_FORMAT.get().format(System.currentTimeMillis()));
    }
}
