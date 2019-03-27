/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author maoyz0621 on 19-1-8
 * @version: v1.0
 */
@Configuration
public class TaskConfig implements SchedulingConfigurer {

    /**
     * 定时线程池
     */
    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return new ScheduledThreadPoolExecutor(10, new ThreadFactory() {

            private AtomicInteger atomicInteger = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "taskConfig-" + atomicInteger.getAndIncrement());
            }
        });
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
    }
}
