/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.factory;

import com.myz.log.record.service.IParseFunction;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
public class ParseFunctionFactory {

    private final Map<String, IParseFunction> allFunctionMap;

    public ParseFunctionFactory(List<IParseFunction> parseFunctions) {
        if (parseFunctions == null || parseFunctions.isEmpty()) {
            allFunctionMap = Collections.emptyMap();
            return;
        }
        allFunctionMap = new HashMap<>();
        for (IParseFunction parseFunction : parseFunctions) {
            Method fm = parseFunction.functionName();
            if (fm == null) {
                continue;
            }
            allFunctionMap.put(fm.getName(), parseFunction);
        }
    }

    public IParseFunction getFunction(String functionName) {
        return allFunctionMap.get(functionName);
    }

    public boolean isBeforeFunction(String functionName) {
        return allFunctionMap.get(functionName) != null && allFunctionMap.get(functionName).executeBefore();
    }
}