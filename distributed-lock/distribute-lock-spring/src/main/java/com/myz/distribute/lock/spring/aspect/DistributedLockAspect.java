/**
 * Copyright 2022 Inc.
 **/
package com.myz.distribute.lock.spring.aspect;

import com.myz.distribute.lock.spring.annotation.DistributedLock;
import com.myz.distributed.lock.core.DistributedLockInfo;
import com.myz.distributed.lock.core.DistributedLockServer;
import com.myz.distributed.lock.core.LockKeyGenerator;
import com.myz.distributed.lock.core.config.DistributedLockProperties;
import com.myz.distributed.lock.core.config.LockType;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@Aspect
@Order(200)
@AllArgsConstructor
public class DistributedLockAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLockAspect.class);

    private final DistributedLockServer distributedLockServer;

    private final LockKeyGenerator lockKeyGenerator;

    private final DistributedLockProperties distributedLockProperties;

    @Pointcut(value = "@annotation(com.myz.distribute.lock.spring.annotation.DistributedLock))")
    public void joinPointMethod() {
    }

    @Around(value = "@annotation(com.myz.distribute.lock.spring.annotation.DistributedLock))")
    public Object around(ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        // 获取方法上的注解
        DistributedLock annotation = method.getAnnotation(DistributedLock.class);
        DistributedLockInfo distributedLockInfo = null;
        try {
            String keyPrefix = distributedLockProperties.getKeyPrefix() + ":";
            keyPrefix += StringUtils.hasText(annotation.name()) ? annotation.name() :
                    method.getDeclaringClass().getName() + "." + method.getName();

            String key = keyPrefix + "#" + lockKeyGenerator.generateKey(method, pjp.getArgs(), annotation.keys());

            distributedLockInfo = distributedLockServer.acquire(key, annotation.expire(), annotation.acquireTimeout(), LockType.Reentrant == annotation.lockType());
            if (distributedLockInfo != null) {
                LOGGER.info("============= acquire key=[{}] success =================", key);
                return pjp.proceed();
            }
            // 获取锁失败
            annotation.timeOutStrategy().handleLockTimeOut();
        } catch (Throwable throwable) {
            LOGGER.error("", throwable);
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