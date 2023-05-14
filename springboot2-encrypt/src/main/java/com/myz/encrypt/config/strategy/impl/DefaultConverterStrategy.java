/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.config.strategy.impl;

import com.myz.encrypt.config.strategy.ConverterStrategy;
import org.springframework.stereotype.Component;

/**
 * @author maoyz0621 on 2023/5/11
 * @version v1.0
 */
@Component
public class DefaultConverterStrategy implements ConverterStrategy {

    @Override
    public Object convert(Object obj) {
        return obj;
    }
}