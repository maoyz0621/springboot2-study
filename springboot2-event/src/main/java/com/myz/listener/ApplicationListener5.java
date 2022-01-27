/**
 * Copyright 2022 Inc.
 **/
package com.myz.listener;

import com.myz.event.ErrorEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * @author maoyz0621 on 2022/1/22
 * @version v1.0
 */
@Component
@Slf4j
public class ApplicationListener5<ApplicationEvent> {

    //////////////////////////// ErrorEvent start /////////////////////////////

    /**
     * 发布事件的事务结束之后才会执行监听方法 @TransactionalEventListener
     * fallbackExecution = false，发布事件没有事务控制时，不执行监听事件
     * TransactionPhase = AFTER_COMMIT，当前事务提交之后才会执行监听方法
     */
    @TransactionalEventListener(classes = ErrorEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void onEventTransactional(ApplicationEvent event) throws InterruptedException {
        Thread.sleep(1000);
        log.info("ApplicationListener5 ErrorEvent TransactionalEventListener = {}", event);
    }

    @TransactionalEventListener(classes = ErrorEvent.class, fallbackExecution = true)
    public void onEventTransactionalWithFallbackExecution(ApplicationEvent event) throws InterruptedException {
        Thread.sleep(1000);
        log.info("ApplicationListener5 ErrorEvent TransactionalEventListener WithFallbackExecution = {}", event);
    }

    @EventListener(classes = ErrorEvent.class)
    public void onErrorEvent(ApplicationEvent event) throws InterruptedException {
        Thread.sleep(1000);
        log.info("ApplicationListener5 ErrorEvent EventListener = {}", event);
    }
    //////////////////////////// ErrorEvent end /////////////////////////////
}