/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.async.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

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
    private static final Logger log = LoggerFactory.getLogger(AsyncTask.class);

    /**
     * 无返回值
     */
    @Async
    public void asyncInvokeSimple() throws InterruptedException {
        log.debug("asyncInvokeSimple() start...");

        TimeUnit.SECONDS.sleep(5);

        log.debug("asyncInvokeSimple() end ...");
    }

    /**
     * 带参数的异步调用 异步方法可以传入参数
     * 对于返回值是void，异常会被AsyncUncaughtExceptionHandler处理掉
     */
    @Async
    public void asyncInvokeWithParameter(String s) {
        log.info("asyncInvokeWithParameter, parameter = {}", s);
        throw new IllegalArgumentException(s);
    }


    /**
     * 有返回值
     * 对于返回值是Future，不会被AsyncUncaughtExceptionHandler处理，需要我们在方法中捕获异常并处理
     * 或者在调用方在调用Future.get()时捕获异常进行处理
     * future.isDone()  任务完成
     * future.get()     返回值
     */
    @Async
    public Future<String> asyncInvokeReturnFuture(int i) {
        log.debug("asyncInvokeReturnFuture() start...");
        Future future;
        try {
            TimeUnit.SECONDS.sleep(6);
            future = new AsyncResult<String>("success: " + i);
        } catch (InterruptedException e) {
            future = new AsyncResult<String>("buildFailure: " + i);
        }
        return future;
    }

}
