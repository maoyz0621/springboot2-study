package com.myz.springboot2.retry.dynamic;


import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author maoyz
 */
public class DynamicCglibProxy<T> implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicCglibProxy.class);

    private static final int DEFAULT_RETRY_TIMES = 3;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        LOGGER.info("执行DynamicCglibProxy, 被代理类proxy = {}, method = {}, 返回类型returnType = {}, 传递参数args = {}", obj.getClass(), method.getName(), method.getReturnType(), args);

        Object result = null;
        int times = 0;
        while (times < DEFAULT_RETRY_TIMES) {
            try {
                // 通过代理子类调用父类的方法, 不要使用invoke(), 不要使用invoke(),不要使用invoke()
                result = proxy.invokeSuper(obj, args);
                LOGGER.info("返回结果result = {}", result);
                return result;
            } catch (Exception ex) {
                times++;

                TimeUnit.SECONDS.sleep(2);

                if (times >= 3) {
                    throw new RuntimeException("重试完成", ex);
                }
            }
        }
        return result;
    }

}
