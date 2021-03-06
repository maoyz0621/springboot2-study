/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.spring.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 任务类1
 *
 * @author maoyz on 2018/8/7
 * @version: v1.0
 */
@Component
public class FirstTask extends BaseTask {

    private static final Logger logger = LoggerFactory.getLogger(FirstTask.class);

    public void task() {
        logger.info("****************** 执行FirstTask Start, {} *************************", DATE_FORMAT.get().format(new Date()));
        executorService.execute(() -> {
            // 模拟业务延时
            try {
                Thread.sleep(50000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
            logger.info("****************** 执行FirstTask End, {} *************************", DATE_FORMAT.get().format(new Date()));
        });
    }
}
