/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.log.enums;

/**
 * 日志操作模块
 *
 * @author maoyz0621 on 2018/12/28
 * @version: v1.0
 */
public enum LogEventType {

    DEFAULT("1", "default"), ADD("2", "add"), UPDATE("3", "update"), DELETE_SINGLE("4", "delete-single"),
    LOGIN("10", "login"), LOGIN_OUT("11", "login_out");

    private String event;
    private String name;

    LogEventType(String event, String name) {
        this.event = event;
        this.name = name;
    }

    public String getEvent() {
        return event;
    }

    public String getName() {
        return name;
    }
}
