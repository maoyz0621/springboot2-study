/**
 * Copyright 2022 Inc.
 **/
package com.myz.config.enable.lock;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

/**
 * @author maoyz0621 on 2022/5/12
 * @version v1.0
 */
@Aspect
@Order(200)
@AllArgsConstructor
@Slf4j
public class DistributedLockAspect {


    @Pointcut("@annotation(com.myz.config.enable.lock.DistributedLock))")
    public void joinPointMethod() {
    }

    @Around(value = "joinPointMethod()")
    public Object around(ProceedingJoinPoint pjp) {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        // 获取方法上的注解
        DistributedLock annotation = methodSignature.getMethod().getAnnotation(DistributedLock.class);
        log.info("DistributedLock = {}", annotation);
        if (annotation == null) {
            try {
                return pjp.proceed();
            } catch (Throwable e) {

            }
        }
        return null;
    }
}