package com.myz.springboot2.retry.web;

import com.myz.springboot2.retry.dynamic.DynamicCglibProxyFactory;
import com.myz.springboot2.retry.dynamic.DynamicJdkProxyFactory;
import com.myz.springboot2.retry.dynamic.DynamicProxyFactory;
import com.myz.springboot2.retry.service.IRetryService;
import com.myz.springboot2.retry.service.RetryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author maoyz
 */
@RestController
public class RetryController {

    @Autowired
    private IRetryService retryService;

    @RequestMapping("/retry/{num}")
    public String retry(@PathVariable("num") Integer num) {
        return String.valueOf(retryService.retry(num));
    }

    /**
     * 使用接口实现类
     */
    @RequestMapping("/jdkRetry0/{num}")
    public String jdkRetry0(@PathVariable("num") Integer num) {
        // 实现类
        IRetryService newInstance = new DynamicJdkProxyFactory<>(RetryServiceImpl.class).newInstance();

        return String.valueOf(newInstance.jdkRetry(num));
    }

    /**
     * 使用接口类 xxxxxxxx
     */
    @RequestMapping("/jdkRetry1/{num}")
    public String jdkRetry1(@PathVariable("num") Integer num) {
        // 接口类
        IRetryService newInstance = new DynamicJdkProxyFactory<>(IRetryService.class).newInstance();

        return String.valueOf(newInstance.jdkRetry(num));
    }

    /**
     * cglib
     */
    @RequestMapping("/cglibRetry/{num}")
    public String cglibRetry(@PathVariable("num") Integer num) {
        // 实现类
        RetryServiceImpl newInstance = new DynamicCglibProxyFactory<>(RetryServiceImpl.class).newInstance();

        return String.valueOf(newInstance.cglibRetry(num));
    }

    @RequestMapping("/factoryRetry/{num}")
    public String factoryRetry(@PathVariable("num") Integer num) {
        // 实现类
        IRetryService newInstance = new DynamicProxyFactory<>(RetryServiceImpl.class).newInstance();

        return String.valueOf(newInstance.cglibRetry(num));
    }

}
