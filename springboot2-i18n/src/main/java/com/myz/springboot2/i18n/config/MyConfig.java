/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.i18n.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author maoyz0621 on 2020/9/30
 * @version v1.0
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {

    public static final String VALIDATION_MESSAGES_PATH = "i18n/validationMessages";

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

    @Bean("validationMessageSource")
    public MessageSource validationMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 指定参数验证国际化的Resource Bundle地址
        messageSource.setBasename(VALIDATION_MESSAGES_PATH);
        //指定国际化的默认编码
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * 重新设置Validator的配置文件所在位置
     *
     * @return
     */
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(validationMessageSource());
        // 如果要求验证一个错误就返回异常，设置true
        // localValidatorFactoryBean.getValidationPropertyMap().put(HibernateValidatorConfiguration.FAIL_FAST, "true");
        return localValidatorFactoryBean;
    }
}