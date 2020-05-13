/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-05-13 15:31  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.spi;

import org.apache.dubbo.common.extension.SPI;

/**
 * @author maoyz
 */
@SPI
public interface Robot {

    void sayHello();

}