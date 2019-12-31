package com.myz.springboot2.retry.dynamic;

import net.sf.cglib.proxy.Enhancer;

/**
 * @author maoyz
 */
public class DynamicCglibProxyFactory<T> {

    private final Class<T> target;

    public DynamicCglibProxyFactory(Class<T> target) {
        this.target = target;
    }

    public T newInstance() {
        Enhancer enhancer = new Enhancer();
        // 目标对象类
        enhancer.setSuperclass(target);
        enhancer.setCallback(new DynamicCglibProxy());
        return (T) enhancer.create();
    }

}
