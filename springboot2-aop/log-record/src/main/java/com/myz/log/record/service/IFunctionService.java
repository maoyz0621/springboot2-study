/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.service;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public interface IFunctionService {

    String apply(String functionName, String value);

    boolean beforeFunction(String functionName);
}