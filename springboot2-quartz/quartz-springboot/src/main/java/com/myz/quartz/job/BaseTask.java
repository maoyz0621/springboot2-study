/**
 * Copyright 2019 Inc.
 **/
package com.myz.quartz.job;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.*;

/**
 * @author maoyz0621 on 19-5-19
 * @version: v1.0
 */
public abstract class BaseTask extends QuartzJobBean implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(BaseTask.class);

    protected ApplicationContext applicationContext;

    protected ExecutorService taskExecutor;

    /**
     * 使用ThreadLocal实现函数式编程
     */
    protected static final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));

    @Override
    public void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        Trigger trigger = context.getTrigger();
        logger.info("================== JobGroup = {}, JobKey = {}, JObDescription = {}, jobDataMap = {}\n " +
                        "TriggerGroup = {}, TriggerKey = {}, TriggerDescription = {}, TriggerJobDataMap = {}, 定时任务开始时间 = {} ======================",
                jobDetail.getKey().getGroup(),
                jobDetail.getKey().getName(),
                jobDetail.getDescription(),
                jobDetail.getJobDataMap(),
                trigger.getKey().getGroup(),
                trigger.getKey().getName(),
                trigger.getDescription(),
                trigger.getJobDataMap(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trigger.getStartTime()));

        executeTask();
    }

    /**
     * 执行任务
     */
    protected abstract void executeTask();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.taskExecutor = (ExecutorService) applicationContext.getBean("taskExecutor");
    }
}
