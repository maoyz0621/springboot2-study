/**
 * Copyright 2019 Inc.
 **/
package com.myz.timer.spring.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author maoyz0621 on 19-5-19
 * @version: v1.0
 */
@Configuration
public class QuartzConfig {

    /**
     * 全局线程池
     */
    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService() {
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
