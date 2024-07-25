/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.annotation;

import com.myz.log.opt.core.model.BizCategoryEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OperationLog {

    BizCategoryEnum bizCategory();

    String bizTarget();

    String bizNo();

    /**
     * see {@link com.myz.log.opt.core.support.diff.DiffSelectorMethod}
     * <p>
     * e.g. "xxx.VpcService#findVpcById(Long)"
     */
    String diffSelector() default "";
}