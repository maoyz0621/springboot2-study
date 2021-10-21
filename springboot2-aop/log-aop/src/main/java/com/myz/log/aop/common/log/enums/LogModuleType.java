/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.log.aop.common.log.enums;

/**
 * 日志功能模块
 *
 * @author maoyz0621 on 2018/12/28
 * @version: v1.0
 */
public enum LogModuleType {
    /**
     * 默认值
     */
    DEFAULT("1"),
    /**
     * 系统模块
     */
    SYS("2"),
    /**
     * 功能模块
     */
    MODULE("3");

    private final String module;

    LogModuleType(String index) {
        this.module = index;
    }

    public String getModule() {
        return module;
    }
}
