/*
 * Copyright (C) 2019, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-08 15:10  Inc. All rights reserved.
 */
package com.myz.dubbo.base.interfaces;

/**
 * @author maoyz
 */
public interface DemoService {

    String sayHello(String name);

    /**
     * java 异常
     */
    void except();

    /**
     * RuntimeException
     */
    void exceptRuntime();

    /**
     * 自定义业务异常 BusinessException
     */
    void exceptBusiness();

    /**
     * RpcException
     */
    void exceptDubbo();

    /**
     * 重试
     */
    String retry();

}