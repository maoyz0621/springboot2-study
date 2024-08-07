package com.myz.log.opt.core.model;

import org.aopalliance.intercept.MethodInvocation;

/**
 * @author dukui
 */
public class ParsableBizInfo {

    private MethodInvocation methodInvocation;

    private Object result;

    private String originParsableTarget;

    public ParsableBizInfo(MethodInvocation methodInvocation, Object result, String originParsableTarget) {
        this.methodInvocation = methodInvocation;
        this.result = result;
        this.originParsableTarget = originParsableTarget;
    }

    public MethodInvocation getMethodInvocation() {
        return methodInvocation;
    }

    public void setMethodInvocation(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getOriginParsableTarget() {
        return originParsableTarget;
    }

    public void setOriginParsableTarget(String originParsableTarget) {
        this.originParsableTarget = originParsableTarget;
    }
}