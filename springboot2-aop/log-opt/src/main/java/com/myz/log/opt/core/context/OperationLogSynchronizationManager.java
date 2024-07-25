/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.context;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;

import static com.myz.log.opt.core.constant.Constants.CONTEXT_STRATEGY_SYSTEM_PROPERTY;
import static com.myz.log.opt.core.constant.Constants.DEFAULT_CONTEXT_STRATEGY;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public class OperationLogSynchronizationManager {
    private static String strategyName = System.getProperty(CONTEXT_STRATEGY_SYSTEM_PROPERTY);

    private static OperationLogContextImplStrategy strategy;

    static {
        initialize();
    }

    private OperationLogSynchronizationManager() {}

    private static void initialize() {
        if (!StringUtils.hasText(strategyName)) {
            strategyName = DEFAULT_CONTEXT_STRATEGY;
        }

        if (strategyName.equals(DEFAULT_CONTEXT_STRATEGY)) {
            strategy = new ThreadLocalOperationLogContextImplStrategy();
        } else {
            try {
                Class<?> clazz = Class.forName(strategyName);
                Constructor<?> customStrategy = clazz.getConstructor();
                strategy = (OperationLogContextImplStrategy) customStrategy.newInstance();
            } catch (Exception ex) {
                ReflectionUtils.handleReflectionException(ex);
            }
        }
    }

    public static OperationLogContext getContext() {
        return strategy.getContext();
    }

    public static OperationLogContext register(OperationLogContext context) {
        return strategy.setContext(context);
    }

    public static OperationLogContext clear() {
        return strategy.clearContext();
    }
}