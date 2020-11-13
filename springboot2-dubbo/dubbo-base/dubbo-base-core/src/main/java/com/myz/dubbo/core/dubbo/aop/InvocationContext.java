/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-11-13 15:44  Inc. All rights reserved.
 */
package com.myz.dubbo.core.dubbo.aop;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Arrays;

/**
 * @author maoyz
 */
public class InvocationContext {
    public static final String TEMPLATE = "%s.%s(%s)";
    private final Class targetClass;
    private final String targetMethod;
    private final Object[] args;

    public InvocationContext(Class targetClass, String targetMethod, Object[] args) {
        this.targetClass = targetClass;
        this.targetMethod = targetMethod;
        this.args = args;
    }

    public Class getTargetClass() {
        return this.targetClass;
    }

    public String getTargetMethod() {
        return this.targetMethod;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public boolean equals(Object that) {
        return EqualsBuilder.reflectionEquals(this, that, new String[0]);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[0]);
    }

    public String toString() {
        return String.format("%s.%s(%s)", this.targetClass.getName(), this.targetMethod, Arrays.toString(this.args));
    }
}