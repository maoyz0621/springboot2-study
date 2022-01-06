/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.utils;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author maoyz0621 on 2021/10/31
 * @version v1.0
 */
public class AnnotationFinder<T extends Annotation> {
    public static ConcurrentMap<Class, Set<String>> annotationFieldMap = new ConcurrentHashMap<>(256);
    public static ConcurrentMap<Class, Set<Method>> annotationMethodMap = new ConcurrentHashMap<>(256);
    public static Set<Class> set = new HashSet<>(256);

    /**
     * class注解
     *
     * @param clazz
     */
    public void findAnnotationClass(Class clazz, Class<T> annotationClass) {
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (clazz.isAnnotationPresent(annotationClass)) {
                set.add(clazz);
                break;
            }
        }
    }

    /**
     * field注解
     *
     * @param clazz
     */
    public void findAnnotationField(Class clazz, Class<T> annotationClass) {
        if (annotationFieldMap.get(clazz) != null) {
            return;
        }
        Set<String> annotationField = Arrays.stream(FieldUtils.getAllFields(clazz))
                .filter(field -> field.isAnnotationPresent(annotationClass))
                .map(Field::getName)
                .collect(Collectors.toSet());
        annotationFieldMap.put(clazz, annotationField);
    }

    /**
     * method注解
     *
     * @param clazz
     */
    public void findAnnotationMethod(Class clazz, Class<T> annotationClass) {
        Set<Method> annotationMethod = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .collect(Collectors.toSet());
        annotationMethodMap.put(clazz, annotationMethod);
    }
}