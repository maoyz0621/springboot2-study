/**
 * Copyright 2022 Inc.
 **/
package com.myz;

import com.myz.handler.DbEventHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author maoyz0621 on 2022/1/23
 * @version v1.0
 */
@SpringBootApplication
public class Springboot2EventApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Springboot2EventApplication.class, args);
        // context.getBean(EventHandler.class).doHandlerMyEvent();
        // context.getBean(EventHandler.class).doHandlerErrorEvent();
        // context.getBean(EventHandler.class).doHandlerErrorEventException();

        // context.getBean(SpringEventServer.class).bind();



        // @Transactional事务执行
        // context.getBean(DbEventHandler.class).doHandlerErrorEventTransactional();


        // @Transactional事务执行异常
        context.getBean(DbEventHandler.class).doHandlerErrorEventTransactionalException();
    }
}