/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.jackson.config.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author maoyz0621 on 2021/2/6
 * @version v1.0
 */
public class DateToLongFormatterFactory implements AnnotationFormatterFactory<DateToLongFormat> {

    private final Set<Class<?>> fieldTypes;

    public DateToLongFormatterFactory() {
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(Long.class);
        this.fieldTypes = set;
    }


    @Override
    public Set<Class<?>> getFieldTypes() {
        return fieldTypes;
    }

    @Override
    public Printer<?> getPrinter(DateToLongFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation);
    }

    @Override
    public Parser<?> getParser(DateToLongFormat annotation, Class<?> fieldType) {
        return configureFormatterFrom(annotation);
    }

    private Formatter<Long> configureFormatterFrom(DateToLongFormat annotation) {
        String pattern = annotation.pattern();
        if (StringUtils.hasLength(pattern)) {
            return new DateToLongFormatter(pattern);
        }

        try {
            return new DateToLongFormatter("yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            return new DateToLongFormatter("yyyy-MM-dd");
        }
    }
}