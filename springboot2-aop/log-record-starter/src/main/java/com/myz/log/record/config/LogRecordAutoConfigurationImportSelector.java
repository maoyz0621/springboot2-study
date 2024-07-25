/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.record.config;

import com.myz.log.record.advisor.LogRecordInterceptor;
import com.myz.log.record.advisor.advice.BeanFactoryLogRecordAdvisor;
import com.myz.log.record.annotation.EnableLogRecord;
import com.myz.log.record.aspect.LogRecordAspect;
import com.myz.log.record.core.LogRecordValueParser;
import com.myz.log.record.core.expression.LogRecordExpressionEvaluator;
import com.myz.log.record.factory.LogRecordOptionFactory;
import com.myz.log.record.factory.ParseFunctionFactory;
import com.myz.log.record.service.IFunctionService;
import com.myz.log.record.service.IOperatorGetService;
import com.myz.log.record.service.IParseFunction;
import com.myz.log.record.service.impl.DefaultOperatorGetServiceImpl;
import com.myz.log.record.store.AbstractLogRecordService;
import com.myz.log.record.store.DefaultLogRecordServiceImpl;
import com.myz.log.record.store.ILogRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
@Configuration(proxyBeanMethods = true)
public class LogRecordAutoConfigurationImportSelector implements ImportAware {

    private static final Logger log = LoggerFactory.getLogger(LogRecordProxyAutoConfiguration.class);

    private AnnotationAttributes enableLogRecord;

    private String tenant = "";

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LogRecordExpressionEvaluator logRecordExpressionEvaluator() {
        return new LogRecordExpressionEvaluator();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ParseFunctionFactory parseFunctionFactory(@Autowired List<IParseFunction> parseFunctions) {
        return new ParseFunctionFactory(parseFunctions);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LogRecordOptionFactory logRecordOperationFactory(@Autowired ParseFunctionFactory parseFact) {
        return new LogRecordOptionFactory(parseFact);
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LogRecordValueParser logRecordValueParser(LogRecordExpressionEvaluator logRecordExpressionEvaluator) {
        return new LogRecordValueParser(null, logRecordExpressionEvaluator);
    }

    @Bean
    @ConditionalOnMissingBean(IOperatorGetService.class)
    @Role(BeanDefinition.ROLE_APPLICATION)
    public IOperatorGetService operatorGetService() {
        return new DefaultOperatorGetServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(AbstractLogRecordService.class)
    @Role(BeanDefinition.ROLE_APPLICATION)
    public ILogRecordService recordService() {
        return new DefaultLogRecordServiceImpl();
    }

    @Bean
    public LogRecordAspect logRecordAspect() {
        return new LogRecordAspect();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryLogRecordAdvisor logRecordAdvisor(IFunctionService functionService) {
        BeanFactoryLogRecordAdvisor advisor = new BeanFactoryLogRecordAdvisor();
        // advisor.setLogRecordOperationSource(logRecordOperationSource());
        advisor.setAdvice(logRecordInterceptor(functionService));
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public LogRecordInterceptor logRecordInterceptor(IFunctionService functionService) {
        LogRecordInterceptor interceptor = new LogRecordInterceptor();
        // interceptor.setLogRecordOperationSource(logRecordOperationSource());
        interceptor.setTenant(enableLogRecord.getString("tenant"));
        interceptor.setFunctionService(functionService);
        return interceptor;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        enableLogRecord = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableLogRecord.class.getName(), false));
        if (enableLogRecord == null) {
            log.info("@EnableLogRecord is not present on importing class");
            return;
        }
        tenant = enableLogRecord.getString("tenant");
    }
}