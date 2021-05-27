/**
 * Copyright 2019 Inc.
 **/
package com.myz.quartz.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * @author maoyz0621 on 19-5-19
 * @version: v1.0
 */
@Configuration
public class QuartzConfig {

    @Autowired
    private DataSource dataSource;

    /**
     * <property name="overwriteExistingJobs" value="true"/>
     * <!--必须的，QuartzScheduler 延时启动，应用启动完后 QuartzScheduler 再启动 -->
     * <property name="startupDelay" value="3"/>
     * <property name="autoStartup" value="true"/>
     * <property name="waitForJobsToCompleteOnShutdown" value="true"/>
     * <property name="triggers">
     * <list>
     * <ref bean="firstCronTrigger"/>
     * <ref bean="secondCronTrigger"/>
     * </list>
     * </property>
     * <property name="configLocation" value="classpath:quartz.properties"/>
     * <!-- 指定spring容器的key，如果不设定在job中的jobmap中是获取不到spring容器的 -->
     * <property name="applicationContextSchedulerContextKey" value="applicationContext"/>
     *
     * @return
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setStartupDelay(3);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        // quartz配置属性
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        return schedulerFactoryBean;
    }

    /**
     * 提供quartz配置属性
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        // 在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * quartz监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        QuartzInitializerListener quartzInitializerListener = new QuartzInitializerListener();
        return quartzInitializerListener;
    }

    /**
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

    /**
     * 全局任务线程池
     * todo 在BaseTask中死活注入为 null 解决办法: 通过 implements ApplicationContextAware --> this.taskExecutor = (ExecutorService) applicationContext.getBean("taskExecutor");
     */
    @Bean(name = "taskExecutor", destroyMethod = "shutdown")
    public ExecutorService taskExecutor() {
        // 创建ThreadFactory
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("task_thread_pool_%d").build();
        ExecutorService executorService = new ThreadPoolExecutor(
                100,
                150,
                5,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(20),
                threadFactory,
                new ThreadPoolExecutor.AbortPolicy());
        return executorService;
    }
}
