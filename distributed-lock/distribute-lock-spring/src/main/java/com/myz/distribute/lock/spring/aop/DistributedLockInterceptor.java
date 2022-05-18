/**
 * Copyright 2022 Inc.
 **/
package com.myz.distribute.lock.spring.aop;

import com.myz.distribute.lock.spring.annotation.DistributedLock;
import com.myz.distributed.lock.core.DistributedLockInfo;
import com.myz.distributed.lock.core.DistributedLockServer;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.AopProxyUtils;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@RequiredArgsConstructor
public class DistributedLockInterceptor implements MethodInterceptor {

    private final DistributedLockServer distributedLockServer;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(invocation.getThis());

        if (targetClass.equals(invocation.getThis().getClass())) {
            return invocation.proceed();
        }

        // 获取方法上的注解
        DistributedLock annotation = invocation.getMethod().getAnnotation(DistributedLock.class);
        if (annotation == null) {
            return invocation.proceed();
        }

        DistributedLockInfo distributedLockInfo = null;
        try {
            distributedLockInfo = distributedLockServer.acquire(annotation.name(), annotation.expire(), annotation.acquireTimeout());
            if (distributedLockInfo != null) {
                return invocation.proceed();
            }
            // 获取锁失败
            throw new RuntimeException();
        } finally {
            if (distributedLockInfo != null && annotation.autoRelease()) {
                boolean releaseLock = distributedLockServer.releaseLock(distributedLockInfo);
                if (!releaseLock) {

                }
            }
        }

    }
}