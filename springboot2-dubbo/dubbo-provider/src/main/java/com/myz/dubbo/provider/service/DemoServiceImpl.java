/*
 * Copyright (C) 2019, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-08 15:13  Inc. All rights reserved.
 */
package com.myz.dubbo.provider.service;

import com.myz.dubbo.base.interfaces.DemoService;
import com.myz.dubbo.core.BusinessException;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz
 */
@org.apache.dubbo.config.annotation.Service(version = "1.0.0")
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        Object userInfo = RpcContext.getContext().getAttachment("userInfo");
        System.out.println(userInfo);
        return "Hello World";
    }

    @Override
    public void except() {
        int i = 1 / 0;
    }

    @Override
    public void exceptRuntime() {
        throw new RuntimeException("exceptRuntime");
    }

    @Override
    public void exceptBusiness() {
        throw new BusinessException("business");
    }

    @Override
    public void exceptDubbo() {

    }

    @Override
    public String retry() {
        System.out.println("retry " + LocalDateTime.now());
        try {
            // 模拟网络延迟
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {

        }
        return "retry";
    }
}