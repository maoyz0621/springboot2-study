package com.myz.log.opt.core.support;

import org.aopalliance.intercept.MethodInvocation;


/**
 * @author maoyz0621
 */
public abstract class MethodInvocationOperationLogCallback<T, E extends Throwable> implements OperationLogCallback<T, E> {

	protected final MethodInvocation invocation;

	protected MethodInvocationOperationLogCallback(MethodInvocation invocation) {
		this.invocation = invocation;
	}

	public MethodInvocation getInvocation() {
		return invocation;
	}

}
