/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public class OperationLogPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private transient Pointcut pointcut;

    public OperationLogPointcutAdvisor() {
    }

    public OperationLogPointcutAdvisor(Pointcut pointcut, Advice advice) {
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