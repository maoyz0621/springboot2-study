/**
 * Copyright 2019 Inc.
 **/
package com.myz.async.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * @author maoyz0621 on 19-7-9
 * @version: v1.0
 */
@Component
public class ContextClosedHandler implements ApplicationListener<ContextClosedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ContextClosedHandler.class);

    @Autowired
    @Qualifier("poolTaskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    private static int WAIT_TIME = 30;

    /**
     * 关闭程序时执行
     *
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("****************** listen ThreadPool shutDown ***********************");

        shutdownAndAwaitTermination(taskExecutor.getThreadPoolExecutor());
    }

    private void shutdownAndAwaitTermination(ExecutorService pool) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(WAIT_TIME, TimeUnit.SECONDS)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(WAIT_TIME, TimeUnit.SECONDS)) {
                    logger.error("********************** shutDown pool buildFailure *****************************");
                }
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
