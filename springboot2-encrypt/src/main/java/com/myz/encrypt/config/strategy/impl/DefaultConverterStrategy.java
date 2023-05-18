/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.config.strategy.impl;

import com.myz.encrypt.config.strategy.ConverterStrategy;
import com.myz.encrypt.config.strategy.constant.ConverterConstants;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author maoyz0621 on 2023/5/11
 * @version v1.0
 */
@Component
@Qualifier(ConverterConstants.DEFAULT_CONVERT)
public class DefaultConverterStrategy implements ConverterStrategy {

    @Override
    public Object convert(Object obj) {
        if (obj instanceof String) {
            return obj + "_************";
        } else if (obj instanceof Long) {
            return Long.parseLong("999" + obj + "000");
        }
        return obj;
    }
}