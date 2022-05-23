/**
 * Copyright 2022 Inc.
 **/
package com.myz.distribute.lock.spring.aop;

import com.myz.distribute.lock.spring.annotation.DistributedLock;
import com.myz.distributed.lock.core.DistributedLockInfo;
import com.myz.distributed.lock.core.DistributedLockServer;
import com.myz.distributed.lock.core.LockKeyGenerator;
import com.myz.distributed.lock.core.config.DistributedLockProperties;
import com.myz.distributed.lock.core.config.LockType;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.util.StringUtils;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@RequiredArgsConstructor
public class DistributedLockInterceptor implements MethodInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLockInterceptor.class);

    private final DistributedLockServer distributedLockServer;
    private final LockKeyGenerator lockKeyGenerator;
    private final DistributedLockProperties distributedLockProperties;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(invocation.getThis());

        if (!targetClass.equals(invocation.getThis().getClass())) {
            LOGGER.warn("使用其他aop组件时,aop切了两次.");
            return invocation.proceed();
        }

        // 获取方法上的注解
        DistributedLock annotation = invocation.getMethod().getAnnotation(DistributedLock.class);
        DistributedLockInfo distributedLockInfo = null;
        try {
            String keyPrefix = distributedLockProperties.getKeyPrefix() + ":";
            keyPrefix += StringUtils.hasText(annotation.name()) ? annotation.name() :
                    invocation.getMethod().getDeclaringClass().getName() + "." + invocation.getMethod().getName();

            String key = keyPrefix + "#" + lockKeyGenerator.generateKey(invocation.getMethod(), arguments, annotation.keys());
            distributedLockInfo = distributedLockServer.acquire(key, annotation.expire(), annotation.acquireTimeout(), annotation.executor(), LockType.Reentrant == annotation.lockType());
            if (distributedLockInfo != null) {
                return invocation.proceed();
            }
            // 获取锁失败
            annotation.timeOutStrategy().handleLockTimeOut();
        } finally {
            if (distributedLockInfo != null && annotation.autoRelease()) {
                boolean releaseLock = distributedLockServer.releaseLock(distributedLockInfo);
                if (!releaseLock) {
                    annotation.releaseTimeoutStrategy().handleReleaseTimeout();
                }
            }
        }
        return null;
    }
}