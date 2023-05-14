/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.config.strategy.impl;

import com.myz.encrypt.config.strategy.ConverterStrategy;
import com.myz.encrypt.config.strategy.EncryptConverter;
import com.myz.encrypt.config.strategy.EncryptConverterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author maoyz0621 on 2023/5/11
 * @version v1.0
 */
@Component
public class EncryptConverterImpl implements EncryptConverter {

    @Autowired
    private EncryptConverterFactory encryptConverterFactory;

    @Override
    public <T> T convert(T obj) {
        ConverterStrategy converterStrategy = encryptConverterFactory.getConverterStrategy();
        return (T) converterStrategy.convert(obj);
    }
}