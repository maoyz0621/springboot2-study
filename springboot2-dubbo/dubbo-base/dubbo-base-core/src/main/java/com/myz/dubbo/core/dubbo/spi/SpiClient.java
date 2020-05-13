/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-13 15:35  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @author maoyz
 */
public class SpiClient {

    public static void main(String[] args) {
        ExtensionLoader<Robot> robots = ExtensionLoader.getExtensionLoader(Robot.class);
        Robot bumblebee = robots.getExtension("bumblebee");
        bumblebee.sayHello();
        System.out.println("\r\n");
        Robot optimusPrime = robots.getExtension("optimusPrime");
        optimusPrime.sayHello();
    }
}