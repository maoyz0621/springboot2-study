/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.support;

import com.myz.log.opt.core.context.OperationLogContext;
import com.myz.log.opt.core.model.BizCategoryEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.UnaryOperator;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public abstract class SimpleOperationLogCallback<T, E extends Throwable> implements OperationLogCallback<T, E> {

    private static final Logger logger = LoggerFactory.getLogger(SimpleOperationLogCallback.class);

    protected BizCategoryEnum bizCategoryEnum;

    protected String bizTarget;

    protected Object bizNo;

    private UnaryOperator<Object> diffSelector;

    protected SimpleOperationLogCallback(BizCategoryEnum bizCategoryEnum, String bizTarget, Object bizNo) {
        this.bizCategoryEnum = bizCategoryEnum;
        this.bizTarget = bizTarget;
        this.bizNo = bizNo;
    }

    protected SimpleOperationLogCallback(BizCategoryEnum bizCategoryEnum, String bizTarget, Object bizNo, UnaryOperator<Object> diffSelector) {
        this(bizCategoryEnum, bizTarget, bizNo);
        this.diffSelector = diffSelector;
    }

    public final BizCategoryEnum getBizCategory() {
        return bizCategoryEnum;
    }

    public final String getBizTarget() {
        return bizTarget;
    }

    public final Object getBizNo() {
        return bizNo;
    }

    public UnaryOperator<Object> getDiffSelector() {
        return diffSelector;
    }

    @Override
    public final T doWithOperationLog(OperationLogContext context) throws E {
        if (logger.isDebugEnabled()) {
            logger.debug("0={======> {} <======}=0", context);
        }
        return doBizAction();
    }

    protected abstract T doBizAction();
}