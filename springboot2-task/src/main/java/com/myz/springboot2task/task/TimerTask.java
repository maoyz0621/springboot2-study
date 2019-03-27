/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2task.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 不同异步方法：定时方法只能返回void且不能接受任务参数
 * @author maoyz0621 on 19-1-7
 * @version: v1.0
 */
@Component
public class TimerTask {

    private static final Logger log = LoggerFactory.getLogger(TimerTask.class);

    /**
     * 使用ThreadLocal实现函数式编程
     */
    private static final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public void task() {
        System.out.println("执行FirstTask..." + DATE_FORMAT.get().format(new Date()));
    }

    /**
     * 基于spring实现的定时任务
     */
    @Scheduled(cron = "${spring.job.corn1}")
    public void schedule() throws InterruptedException {
        log.debug("执行定时任务,cron表达式 {}", DATE_FORMAT.get().format(new Date()));
        // TimeUnit.SECONDS.sleep(20);
    }

}
