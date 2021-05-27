package com.myz.quartz.support;

import com.myz.quartz.exceptions.ScheduleException;
import com.myz.quartz.job.BaseTask;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * 定时任务辅助类
 *
 * @author maoyz0621
 */
@Component
public class ScheduleManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleManager.class);

    @Autowired
    private Scheduler scheduler;

    @SuppressWarnings("unchecked")
    public void addJob(String jobClassName, String jobGroupName, String cronExpression, String jobDescription) throws Exception {
        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getTaskClass(jobClassName))
                .withIdentity(jobClassName, jobGroupName)
                .withDescription(jobDescription)
                .build();
        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobClassName, jobGroupName)
                .withSchedule(scheduleBuilder)
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOGGER.error("创建定时任务失败, {}", e);
            throw new ScheduleException("创建定时任务失败");
        }
    }

    /**
     * 暂停任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    public void jobPause(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 恢复任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    public void jobResume(String jobClassName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 更新定时任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    public void updateScheduleJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            LOGGER.error("更新定时任务失败" + e);
            throw new ScheduleException("更新定时任务失败");
        }
    }

    /**
     * 删除任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    public void jobDelete(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    private Class getTaskClass(@NotNull String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        if (clazz.newInstance() instanceof BaseTask) {
            return clazz;
        } else {
            return BaseTask.class;
        }
    }
}
