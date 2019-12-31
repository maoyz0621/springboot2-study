package com.myz.springboot2.retry.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 参考mybatis MapperProxy
 *
 * @author maoyz
 */
public class DynamicJdkProxy<T> implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicJdkProxy.class);

    private static final Constructor<MethodHandles.Lookup> lookupConstructor;
    private static final Method privateLookupInMethod;
    private static final int ALLOWED_MODES = MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
            | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC;
    private static final int DEFAULT_RETRY_TIMES = 3;
    /**
     * 使用Class
     */
    private final Class<T> interfaceClazz;

    static {
        Method privateLookupIn;
        try {
            privateLookupIn = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
        } catch (NoSuchMethodException e) {
            privateLookupIn = null;
        }
        privateLookupInMethod = privateLookupIn;

        Constructor<MethodHandles.Lookup> lookup = null;
        if (privateLookupInMethod == null) {
            // JDK 1.8
            try {
                lookup = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
                lookup.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(
                        "There is neither 'privateLookupIn(Class, Lookup)' nor 'Lookup(Class, int)' method in java.lang.invoke.MethodHandles.",
                        e);
            } catch (Throwable t) {
                lookup = null;
            }
        }
        lookupConstructor = lookup;
    }

    public DynamicJdkProxy(Class<T> interfaceClazz) {
        this.interfaceClazz = interfaceClazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        LOGGER.info("执行DynamicJdkProxy, 被代理类proxy = {}, method = {}, 返回类型returnType = {}, 传递参数args = {}", proxy.getClass().getInterfaces(), method.getName(), method.getReturnType(), args);

        // Object对象
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
            // 接口方法是default修饰符
        } else if (method.isDefault()) {
            if (privateLookupInMethod == null) {
                return invokeDefaultMethodJava8(proxy, method, args);
            } else {
                return invokeDefaultMethodJava9(proxy, method, args);
            }
        }

        Object result = null;
        int times = 0;
        while (times < DEFAULT_RETRY_TIMES) {
            try {
                if (interfaceClazz.isInterface()) {
                    // 如果只写接口， 不写实现类， 就用此处
                    result = method.invoke(interfaceClazz, args);
                    LOGGER.info("返回结果result = {}", result);
                    return result;
                } else {
                    result = method.invoke(interfaceClazz.newInstance(), args);
                    LOGGER.info("返回结果result = {}", result);
                    return result;
                }
            } catch (Exception e) {
                // 这里可以指定哪些异常类型进行重试
                times++;
                TimeUnit.SECONDS.sleep(2);
                if (times >= 3) {
                    throw new RuntimeException("重试完成", e);
                }
            }
        }

        return result;
    }


    private Object invokeDefaultMethodJava9(Object proxy, Method method, Object[] args)
            throws Throwable {
        final Class<?> declaringClass = method.getDeclaringClass();
        return ((MethodHandles.Lookup) privateLookupInMethod.invoke(null, declaringClass, MethodHandles.lookup()))
                .findSpecial(declaringClass, method.getName(),
                        MethodType.methodType(method.getReturnType(), method.getParameterTypes()), declaringClass)
                .bindTo(proxy).invokeWithArguments(args);
    }

    private Object invokeDefaultMethodJava8(Object proxy, Method method, Object[] args)
            throws Throwable {
        final Class<?> declaringClass = method.getDeclaringClass();
        return lookupConstructor.newInstance(declaringClass, ALLOWED_MODES).unreflectSpecial(method, declaringClass)
                .bindTo(proxy).invokeWithArguments(args);
    }

}
