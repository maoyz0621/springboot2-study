/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.service.impl;

import com.myz.log.record.factory.ParseFunctionFactory;
import com.myz.log.record.service.IFunctionService;
import com.myz.log.record.service.IParseFunction;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public class DefaultFunctionServiceImpl implements IFunctionService {

    private final ParseFunctionFactory parseFunctionFactory;

    public DefaultFunctionServiceImpl(ParseFunctionFactory parseFunctionFactory) {
        this.parseFunctionFactory = parseFunctionFactory;
    }

    @Override
    public String apply(String functionName, String value) {
        IParseFunction function = parseFunctionFactory.getFunction(functionName);
        if (function == null) {
            return value;
        }
        return function.apply(value);
    }

    @Override
    public boolean beforeFunction(String functionName) {
        return parseFunctionFactory.isBeforeFunction(functionName);
    }
}