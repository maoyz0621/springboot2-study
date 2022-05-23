/**
 * Copyright 2022 Inc.
 **/
package com.myz.distribute.lock.spring.aop;

import com.myz.distribute.lock.spring.annotation.DistributedLock;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.lang.NonNull;

/**
 * @author maoyz0621 on 2022/5/22
 * @version v1.0
 */
public class DistributedLockAnnotationAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    /**
     * 拦截method上的注解 #DistributedLock
     */
    private final Pointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(DistributedLock.class);

    private final Advice advice;

    public DistributedLockAnnotationAdvisor(@NonNull DistributedLockInterceptor advice) {
        this(advice, 100);
    }

    public DistributedLockAnnotationAdvisor(@NonNull DistributedLockInterceptor advice, int order) {
        this.advice = advice;
        setOrder(order);
    }


    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }
}