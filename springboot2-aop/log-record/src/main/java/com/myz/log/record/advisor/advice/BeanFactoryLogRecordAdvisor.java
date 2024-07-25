/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.advisor.advice;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author maoyz0621 on 2024/7/26
 * @version v1.0
 */
public class BeanFactoryLogRecordAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    private static final long serialVersionUID = -1762085660993957767L;

    private transient Pointcut pointcut;

    public BeanFactoryLogRecordAdvisor() {
    }

    public BeanFactoryLogRecordAdvisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        setAdvice(advice);
    }

    public void setPointcut(Pointcut pointcut) {
        this.pointcut = pointcut;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}