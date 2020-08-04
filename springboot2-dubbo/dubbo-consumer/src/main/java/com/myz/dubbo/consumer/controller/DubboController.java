/*
 * Copyright (C) 2019, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-08 15:11  Inc. All rights reserved.
 */
package com.myz.dubbo.consumer.controller;

import com.myz.dubbo.base.interfaces.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz
 */
@RestController
public class DubboController {

    // @Reference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")   // N/A使用
    @Reference(version = "1.0.0", retries = 0)   // zk使用
    private DemoService demoService;

    @GetMapping("/index")
    public String index(String msg) {
        return demoService.sayHello(msg);
    }

    @GetMapping("/except")
    public String index() {
        try {
            demoService.except();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "except";
    }

    @GetMapping("/exceptBusiness")
    public String exceptBusiness() {
        try {
            demoService.exceptBusiness();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "exceptBusiness";
    }

    @GetMapping("/exceptRuntime")
    public String exceptRuntime() {
        try {
            demoService.exceptRuntime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @GetMapping("/exceptDubbo")
    public String exceptDubbo() {
        try {
            demoService.exceptDubbo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "exceptRuntime";
    }

    @GetMapping("/retry")
    public String retry() {
        try {
            return demoService.retry();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "buildFailure";
    }
}