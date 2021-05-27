/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.quartz.scheduler;

import com.myz.timer.quartz.jobdetail.FirstJobDetail;
import com.myz.timer.quartz.trigger.FirstTrigger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author maoyz on 2018/8/5
 * @version: v1.0
 */
public class FirstScheduler {

    protected static final Logger logger = LoggerFactory.getLogger(FirstScheduler.class);

    /**
     * @param jobDetail
     * @param trigger
     */
    public static void startJob(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
        // 1 创建scheduler
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        // 4 注册任务和触发器
        Date date = scheduler.scheduleJob(jobDetail, trigger);
        logger.debug("当前时间 = {}， 最近将要执行任务的时间 date = {}",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(date));

        // 5 启动服务
        scheduler.start();

        logger.debug("服务开启时间: {}", new SimpleDateFormat("yyyy-MM-dd HH:mm: SSS").format(new Date()));

        // 服务挂起
        // scheduler.standby();

        // 关闭服务，无法重启服务
        // scheduler.shutdown(false);

        boolean shutdown = scheduler.isShutdown();
        logger.debug("服务关闭 :{}", shutdown);

    }

    public static void main(String[] args) throws SchedulerException {

        JobDetail jobDetail = new FirstJobDetail().jobDetail("firstJob", "first_job");

        Trigger trigger = new FirstTrigger().trigger("firstTrigger", "first_trigger", 5, 3);

        startJob(jobDetail, trigger);
    }
}
