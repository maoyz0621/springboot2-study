/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.timer.springTask.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 异步任务
 *
 * @author maoyz on 2018/8/8
 * @version: v1.0
 */
@Component
public class AsyncTask {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private static final ThreadLocal<DateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"));


    /**
     * 无返回值
     */
    @Async
    public void task1() throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        logger.debug("执行无返回值异步任务");
    }

    /**
     * 有返回值
     *
     * @param i
     * @return
     */
    @Async
    public Future<String> task2(int i) {
        Future future = null;
        try {
            TimeUnit.SECONDS.sleep(6);
            future = new AsyncResult<String>("success:" + i);
        } catch (InterruptedException e) {
            future = new AsyncResult<String>("error:" + i);
        }
        return future;
    }

}
