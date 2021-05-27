/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.springTask;

import com.myz.timer.springTask.async.TimerTask;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 基于spring自身实现的定时任务  @Scheduled
 *
 * @author maoyz on 2018/8/8
 * @version: v1.0
 */
public class SpringScheduleTimer {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        TimerTask task = applicationContext.getBean(TimerTask.class);
        task.schedule();
    }
}
