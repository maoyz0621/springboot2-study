package com.myz.springboot2.retry.dynamic;

import java.lang.reflect.Proxy;

/**
 * 参考mybatis MapperProxyFactory
 *
 * @author maoyz
 */
public class DynamicJdkProxyFactory<T> {

    private final Class<T> interfaceClazz;

    public DynamicJdkProxyFactory(Class<T> interfaceClazz) {
        this.interfaceClazz = interfaceClazz;
    }

    public T newInstance() {
        final DynamicJdkProxy<T> jdkProxy = new DynamicJdkProxy<>(interfaceClazz);
        return newInstance(jdkProxy);
    }

    protected T newInstance(DynamicJdkProxy<T> jdkProxy) {
        // interfaceClazz 分为 接口和Class
        if (interfaceClazz.isInterface()) {
            // T 为接口
            return (T) Proxy.newProxyInstance(
                    interfaceClazz.getClassLoader(),
                    new Class[]{interfaceClazz},
                    jdkProxy);
        } else {
            // T 为实现类
            return (T) Proxy.newProxyInstance(
                    interfaceClazz.getClassLoader(),
                    interfaceClazz.getInterfaces(),
                    jdkProxy);
        }
    }

}
