/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.async.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author maoyz0621 on 19-1-6
 * @version: v1.0
 */
@Configuration
public class AsyncTaskConfig {
    private static final Logger log = LoggerFactory.getLogger(AsyncTaskConfig.class);

    @Bean
    public ContextCopyingDecorator contextCopyingDecorator(){
        return new ContextCopyingDecorator();
    }

    @Bean
    public Executor asyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 最大线程数
        executor.setMaxPoolSize(100);
        // 核心线程数
        executor.setCorePoolSize(20);
        // 排队队列长度
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("Anno-TaskExecutor-");
        // 表明等待所有线程执行完
        executor.setWaitForTasksToCompleteOnShutdown(true);

        // 设置拒绝策略
        executor.setRejectedExecutionHandler((runnable, executor0) -> {

        });

        // 设置线程装饰类
        executor.setTaskDecorator(contextCopyingDecorator());

        // 使用预定义的异常处理类,AbortPolicy, CallerRunsPolicy, DiscardPolicy, DiscardOldestPolicy
        // executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.initialize();
        // (默认为0，此时立即停止), 并没等待xx秒后强制停止
        executor.setAwaitTerminationSeconds(60 * 15);
        return executor;
    }

    /**
     * 自定义异步线程池
     */
    @Bean
    public AsyncTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 最大线程数
        taskExecutor.setMaxPoolSize(100);
        // 核心线程数
        taskExecutor.setCorePoolSize(20);
        // 排队队列长度
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("Anno-TaskExecutor-");
        // 表明等待所有线程执行完
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程装饰类
        taskExecutor.setTaskDecorator(contextCopyingDecorator());


        // 设置拒绝策略
        taskExecutor.setRejectedExecutionHandler((runnable, executor) -> {

        });

        // 使用预定义的异常处理类,AbortPolicy, CallerRunsPolicy, DiscardPolicy, DiscardOldestPolicy
        // taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        taskExecutor.initialize();
        // (默认为0，此时立即停止), 并没等待xx秒后强制停止
        taskExecutor.setAwaitTerminationSeconds(60 * 15);
        return taskExecutor;
    }

    @Bean
    public ThreadPoolTaskExecutor poolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 最大线程数
        taskExecutor.setMaxPoolSize(100);
        // 核心线程数
        taskExecutor.setCorePoolSize(20);
        // 排队队列长度
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("Anno-TaskExecutor-");
        // 表明等待所有线程执行完
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        // 设置线程装饰类
        taskExecutor.setTaskDecorator(new ContextCopyingDecorator());

        // 设置拒绝策略
        taskExecutor.setRejectedExecutionHandler((runnable, executor) -> {

        });

        // 使用预定义的异常处理类,AbortPolicy, CallerRunsPolicy, DiscardPolicy, DiscardOldestPolicy
        // taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        taskExecutor.initialize();
        // (默认为0，此时立即停止), 并没等待xx秒后强制停止
        taskExecutor.setAwaitTerminationSeconds(60 * 15);
        return taskExecutor;
    }

}
