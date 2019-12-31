package com.myz.springboot2.retry.dynamic;

/**
 * @author maoyz
 */
public class DynamicProxyFactory<T> {

    private final Class<T> target;

    public DynamicProxyFactory(Class<T> target) {
        this.target = target;
    }

    public T newInstance() {
        Class<?>[] interfaces = target.getInterfaces();

        // 如果此类没有接口, 调用cglib
        if (interfaces.length == 0) {
            return new DynamicCglibProxyFactory<>(target).newInstance();
            // 基于接口实现类
        } else {
            return new DynamicJdkProxyFactory<>(target).newInstance();
        }
    }
}
