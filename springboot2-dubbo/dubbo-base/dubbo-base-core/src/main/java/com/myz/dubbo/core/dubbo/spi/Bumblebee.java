/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-13 15:33  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.spi;

/**
 * @author maoyz
 */
public class Bumblebee implements Robot {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }
}