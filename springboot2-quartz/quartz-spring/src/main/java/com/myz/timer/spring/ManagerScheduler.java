/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.spring;

import com.myz.timer.spring.manager.QuartzManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 基于QuartzManager启动定时任务
 *
 * @author maoyz on 2018/8/9
 * @version: v1.0
 */
public class ManagerScheduler {

    public static final ApplicationContext APPLICATION = new ClassPathXmlApplicationContext("classpath*:spring-quartz.xml");

    private static Logger logger = LoggerFactory.getLogger(ManagerScheduler.class);

    public static void main(String[] args) {
        QuartzManager manager = APPLICATION.getBean(QuartzManager.class);
        manager.startJobs();
        logger.debug("定时任务开启");
    }
}
