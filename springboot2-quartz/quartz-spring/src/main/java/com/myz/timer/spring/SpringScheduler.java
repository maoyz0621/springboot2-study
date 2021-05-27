/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * spring整合quzrtz启动
 *
 * @author maoyz on 2018/8/8
 * @version: v1.0
 */
public class SpringScheduler {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring-quartz.xml");
    }
}
