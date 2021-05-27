/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.quartz.job;

import org.quartz.*;

import java.text.SimpleDateFormat;

/**
 * 定义要执行的任务
 *
 * @author maoyz on 2018/8/5
 * @version: v1.0
 */
@DisallowConcurrentExecution
public class FirstJob extends BaseJob {

    /**
     * 实现Job
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.debug("FirstJob execute() ...");
        // 获取合并之后的dataMap,相同key会覆盖
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        logger.debug("jobDataMap = {}", jobDataMap);

        // 获取JobDetail
        JobDetail jobDetail = context.getJobDetail();
        logger.debug(jobDetail.getKey().getGroup() + ":"
                + jobDetail.getKey().getName() + ":"
                + jobDetail.getDescription() + ":"
                + jobDetail.getJobClass() + ":"
                // 获取JobDetail中的JobDataMap
                + jobDetail.getJobDataMap() + "-->"
                + jobDetail.getJobDataMap().get("message") + "-->"
                + jobDetail.getJobDataMap().get("second"));

        // 获取Trigger
        Trigger trigger = context.getTrigger();
        logger.debug(trigger.getKey().getGroup() + ":"
                + trigger.getKey().getName() + ":"
                + trigger.getDescription() + ":"
                + trigger.getCalendarName() + ":"
                // 获取Trigger中的JobDataMap
                + trigger.getJobDataMap() + "-->"
                + trigger.getJobDataMap().get("message") + "定时任务开始时间:"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trigger.getStartTime()) + ",定时任务结束时间:"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trigger.getEndTime()));
        logger.debug("FirstJob ... {}:",date);
    }
}
