/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 任务类2
 *
 * @author maoyz on 2018/8/7
 * @version: v1.0
 */
@DisallowConcurrentExecution
public class SecondTask extends BaseTask {

    private static final Logger logger = LoggerFactory.getLogger(SecondTask.class);

    @Override
    public void executeTask() {
        logger.info("****************** 执行SecondTask Start, {}*************************", DATE_FORMAT.get().format(new Date()));
        taskExecutor.execute(() -> {
            // 模拟业务延时
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            logger.info("****************** 执行SecondTask End, {}*************************", DATE_FORMAT.get().format(new Date()));
        });

    }
}
