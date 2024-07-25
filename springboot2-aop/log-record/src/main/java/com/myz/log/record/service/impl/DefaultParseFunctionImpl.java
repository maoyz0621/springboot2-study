/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.service.impl;

import com.myz.log.record.service.IParseFunction;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
@Component
public class DefaultParseFunctionImpl implements IParseFunction {

    @Override
    public Method functionName() {
        try {
            return getClass().getDeclaredMethod("apply", String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        // impossible return null, safe
        return null;
    }

    // static String apply(String value) {
    //     return value;
    // }


    @Override
    public String apply(String value) {
        return value;
    }
}
