/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.annotation;

import com.myz.log.record.config.LogRecordAspectJAutoProxyRegistrar;
import com.myz.log.record.config.LogRecordAutoConfigurationImportSelector;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author maoyz0621 on 2024/7/23
 * @version v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({LogRecordAspectJAutoProxyRegistrar.class, LogRecordAutoConfigurationImportSelector.class})
public @interface EnableLogRecord {

    String tenant() default "";

    AdviceMode mode() default AdviceMode.PROXY;
}