/**
 * Copyright 2023 Inc.
 **/
package com.myz.mybatis.sql.encrypt.utils;

import org.apache.commons.lang3.Validate;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author maoyz0621 on 2023/5/24
 * @version v1.0
 */
public class ReflectionUtils {

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     * <p>
     * 性能较差, 用于单次调用的场景
     */
    public static <T> T getFieldValue(final Object obj, final String fieldName) {
        Field field = getField(obj.getClass(), fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + ']');
        }
        return getFieldValue(obj, field);
    }

    /**
     * 使用已获取的Field, 直接读取对象属性值, 不经过getter函数.
     * <p>
     * 用于反复调用的场景.
     */
    public static <T> T getFieldValue(final Object obj, final Field field) {
        try {
            return (T) field.get(obj);
        } catch (Exception e) {

        }
        return null;
    }

    //////////// 获取Field对象///////////

    /**
     * 循环向上转型, 获取对象的DeclaredField, 并强制设置为可访问.
     * <p>
     * 如向上转型到Object仍无法找到, 返回null.
     * <p>
     * 因为getFiled()不能获取父类的private属性, 因此采用循环向上的getDeclaredField();
     */
    public static Field getField(final Class clazz, final String fieldName) {
        Validate.notNull(clazz, "clazz can't be null");
        Validate.notEmpty(fieldName, "fieldName can't be blank");
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {// NOSONAR
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 改变private/protected的成员变量为可访问，尽量不进行改变，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Field field) {
        if (!field.isAccessible() && (!Modifier.isPublic(field.getModifiers())
                || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers()))) {
            field.setAccessible(true);
        }
    }
}