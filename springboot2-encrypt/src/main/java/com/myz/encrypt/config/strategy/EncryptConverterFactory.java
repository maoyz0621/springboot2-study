/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.config.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author maoyz0621 on 2023/5/11
 * @version v1.0
 */
@Component
public class EncryptConverterFactory {

    @Autowired
    private Map<String, ConverterStrategy> converterStrategyMap;

    public ConverterStrategy getConverterStrategy() {
        return converterStrategyMap.get(null);
    }
}