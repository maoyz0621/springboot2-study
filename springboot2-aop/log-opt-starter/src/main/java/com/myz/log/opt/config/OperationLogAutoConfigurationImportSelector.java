/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.config;

import com.myz.log.opt.annotation.EnableOperationLog;
import com.myz.log.opt.core.advisor.OperationLogPointcutAdvisor;
import com.myz.log.opt.core.advisor.advice.OperationLogInterceptor;
import com.myz.log.opt.core.advisor.pointcut.OperationLogPointcut;
import com.myz.log.opt.core.service.LogRecordPersistenceService;
import com.myz.log.opt.core.service.OperationResultAnalyzerService;
import com.myz.log.opt.core.service.OperatorService;
import com.myz.log.opt.core.service.impl.DefaultLogRecordPersistenceServiceImpl;
import com.myz.log.opt.core.service.impl.DefaultOperationResultAnalyzerServiceImpl;
import com.myz.log.opt.core.service.impl.DefaultOperatorServiceImpl;
import com.myz.log.opt.core.support.OperationLogTemplate;
import com.myz.log.opt.core.support.diff.DiffSelectorSupport;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

import static com.myz.log.opt.core.constant.Constants.ORDER;
import static com.myz.log.opt.core.constant.Constants.TENANT;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
@Configuration(proxyBeanMethods = false)
public class OperationLogAutoConfigurationImportSelector implements ImportAware {

    private AnnotationAttributes enableOperationLog;

    private final ObjectProvider<OpLogProperties> opLogProperties;

    @Autowired
    public OperationLogAutoConfigurationImportSelector(ObjectProvider<OpLogProperties> opLogProperties) {
        this.opLogProperties = opLogProperties;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableOperationLog = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableOperationLog.class.getName(), false));
        if (this.enableOperationLog == null) {
            throw new IllegalArgumentException(
                    "@EnableOperationLog is not present on importing class " + importMetadata.getClassName());
        }
    }

    @Bean
    @ConditionalOnMissingBean(OperationResultAnalyzerService.class)
    public OperationResultAnalyzerService operationResultAnalyzerService() {
        return new DefaultOperationResultAnalyzerServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(OperatorService.class)
    public OperatorService operatorService() {
        return new DefaultOperatorServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean(LogRecordPersistenceService.class)
    public LogRecordPersistenceService logRecordPersistenceService() {
        return new DefaultLogRecordPersistenceServiceImpl();
    }

    @Bean
    public OperationLogTemplate operationLogTemplate(@Qualifier("operationLogConversionService") ConversionService operationLogConversionService,
                                                     OperatorService operatorService,
                                                     LogRecordPersistenceService logRecordPersistenceService,
                                                     OperationResultAnalyzerService operationResultAnalyzerService) {
        String tenant = selectTenant();
        OperationLogTemplate operationLogTemplate = new OperationLogTemplate(tenant, operationLogConversionService);
        operationLogTemplate.setOperatorService(operatorService);
        operationLogTemplate.setLogRecordPersistenceService(logRecordPersistenceService);
        operationLogTemplate.setOperationResultAnalyzerService(operationResultAnalyzerService);
        return operationLogTemplate;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public OperationLogPointcutAdvisor operationLogPointcutAdvisor(OperationLogTemplate operationLogTemplate) {
        OperationLogPointcutAdvisor operationLogPointcutAdvisor = new OperationLogPointcutAdvisor();
        OperationLogPointcut operationLogPointcut = new OperationLogPointcut();
        operationLogPointcutAdvisor.setPointcut(operationLogPointcut);
        operationLogPointcutAdvisor.setAdvice(new OperationLogInterceptor(operationLogTemplate));
        operationLogPointcutAdvisor.setOrder(selectOrder());
        return operationLogPointcutAdvisor;
    }

    @Bean
    public ConversionService operationLogConversionService() {
        return new DefaultConversionService();
    }

    @Bean
    public DiffSelectorSupport diffSelectorSupport() {
        return new DiffSelectorSupport();
    }

    @SuppressWarnings("null")
    private String selectTenant() {
        if (Objects.isNull(this.opLogProperties.getIfAvailable())) {
            return this.enableOperationLog.getString(TENANT);
        }
        return this.opLogProperties.getIfAvailable().getTenant();
    }

    @SuppressWarnings("null")
    private Integer selectOrder() {
        if (Objects.isNull(this.opLogProperties.getIfAvailable())) {
            return this.enableOperationLog.getNumber(ORDER);
        }
        return this.opLogProperties.getIfAvailable().getAdvisor().getOrder();
    }
}
