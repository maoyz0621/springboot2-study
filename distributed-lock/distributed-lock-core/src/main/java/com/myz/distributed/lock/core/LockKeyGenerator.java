/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author maoyz0621 on 2022/5/14
 * @version v1.0
 */
public interface LockKeyGenerator {

    String generateKey(MethodInvocation invocation, String[] definitionKeys);

    String generateKey(Method method, Object[] arguments, String[] definitionKeys);
}