/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.spring;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * @author maoyz on 2018/8/7
 * @version: v1.0
 */
@DisallowConcurrentExecution
public class DetailQuartzJobBean extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(DetailQuartzJobBean.class);

    private String targetObject;
    private String targetMethod;
    private ApplicationContext applicationContext;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.debug("============================ 开始执行JobDetail ===========================");

        // 通过application获取上下文
        Object bean = applicationContext.getBean(targetObject);
        Method method = null;
        try {
            // 获取指定方法
            method = bean.getClass().getMethod(targetMethod, new Class[]{});
            descript(context, bean, method);
            // 执行方法(当前对象,参数)
            method.invoke(bean, new Object[]{});
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 对JobExecutionContext的一个简单描述
     *
     * @param context
     */
    private void descript(JobExecutionContext context, Object bean, Method method) {
        Objects.requireNonNull(bean, "************** Bean can not be null ******************");
        Objects.requireNonNull(method, "************** Method can not be null ******************");
        Objects.requireNonNull(context, "************** JobExecutionContext can not be null ******************");

        JobDetail jobDetail = context.getJobDetail();
        Trigger trigger = context.getTrigger();
        logger.info("================== {}, JobMethod = {}, JobGroup = {}, JobKey = {}, JObDescription = {}, jobDataMap = {}\n " +
                        "TriggerGroup = {}, TriggerKey = {}, TriggerDescription = {}, TriggerJobDataMap = {}, 定时任务开始时间 = {} ======================",
                bean.getClass().getName(),
                method.getName(),
                jobDetail.getKey().getGroup(),
                jobDetail.getKey().getName(),
                jobDetail.getDescription(),
                jobDetail.getJobDataMap(),
                trigger.getKey().getGroup(),
                trigger.getKey().getName(),
                trigger.getDescription(),
                trigger.getJobDataMap(),
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(trigger.getStartTime()));
    }

    public void setTargetObject(String targetObject) {
        this.targetObject = targetObject;
    }

    public void setTargetMethod(String targetMethod) {
        this.targetMethod = targetMethod;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
