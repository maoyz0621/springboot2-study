/**
 * Copyright 2019 Inc.
 **/
package com.myz.springboot2.i18n.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author maoyz0621 on 19-6-30
 * @version v1.0
 */
@Configuration
public class InitializeBean {

    private static final Logger logger = LoggerFactory.getLogger(InitializeBean.class);

    /**
     * 国际化配置信息
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 国际化文件
        Set<String> messagePackages = messageSource.getBasenameSet();
        messagePackages.add("i18n.exceptionMessages");
        // MyConfig中指定了参数验证国际化文件
        // messagePackages.add("i18n.validationMessages");
        messagePackages.add("i18n.commonMessages");
        messagePackages.add("i18n.messages");
        // 设置国际化文件
        messageSource.setBasenames(messagePackages.toArray(new String[0]));
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        logger.info("******************* messageSource {} ********************", messagePackages);
        return messageSource;
    }


    private List<HttpMessageConverter<?>> getDefaultMessageConverter() {
        List<HttpMessageConverter<?>> messageConverters = new LinkedList<>();

        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        messageConverters.add(new ResourceHttpMessageConverter(false));
        messageConverters.add(new SourceHttpMessageConverter<>());
        messageConverters.add(new AllEncompassingFormHttpMessageConverter());
        return messageConverters;
    }

    @Bean
    @ConditionalOnMissingBean({ClientHttpRequestFactory.class})
    public ClientHttpRequestFactory requestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(15000);
        return factory;
    }

    /**
     * 初始化RestTemplate，并优先使用fastjson
     */
    @Bean
    @ConditionalOnMissingBean({RestTemplate.class})
    public RestTemplate restTemplate(ClientHttpRequestFactory factory, HttpMessageConverters messageConverters) {
        RestTemplate restTemplate = new RestTemplate(messageConverters.getConverters());
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }

}
