/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.quartz.trigger;

import org.quartz.*;

import java.util.Date;

/**
 * 触发器
 *
 * @author maoyz on 2018/8/5
 * @version: v1.0
 */
public class FirstTrigger {

    /**
     * 3 设置触发器
     * simpleSchedule
     * <p>
     * cronSchedule 基于日历的任务调用
     * Cron表达式 ：秒 分 小时 日期 月份 星期 年份(可以省略)
     * 其中 * 表示'每'
     */
    public Trigger trigger(String name, String group, int start, int seconds) {

        TriggerKey triggerKey = new TriggerKey(name, group);

        // 设置开启时间点
        Date startTime = new Date(System.currentTimeMillis());
        startTime.setTime(startTime.getTime() + start * 1000L);
        Date endTime = new Date(System.currentTimeMillis());
        endTime.setTime(endTime.getTime() + 10 * 1000L);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription("这是第一个触发器")
                // 设置jodDataMap
                .usingJobData("message", "trigger_message")
                .withIdentity(triggerKey)
                // 设置开始时间
                .startAt(startTime)
                // 设置结束时间
                .endAt(endTime)
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ? *"))  // 执行频率2s
                // .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(seconds).repeatForever())  //执行频率3s,不停断
                // .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(seconds).withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY))  //执行频率3s,不停断
                .build();

        return trigger;
    }
}
