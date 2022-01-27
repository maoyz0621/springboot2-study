/**
 * Copyright 2022 Inc.
 **/
package com.myz.config;

/**
 * @author maoyz0621 on 2022/1/23
 * @version v1.0
 */
public class SpringEventBinder {

    public static void registerOnSpringLoader(Runnable action) {
        try {
            Class.forName("org.springframework.context.ApplicationListener");
        } catch (ClassNotFoundException e) {
            action.run();
            return;
        }
        SpringEventListener.addContextRefreshEventAction(action);
    }
}