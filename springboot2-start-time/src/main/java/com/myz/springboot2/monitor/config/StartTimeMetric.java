package com.myz.springboot2.monitor.config;

import com.google.common.collect.Maps;
import com.myz.springboot2.monitor.model.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.PriorityOrdered;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * postProcessBeforeInstantiation: 实例化前
 * postProcessAfterInstantiation: 实例化后
 * postProcessBeforeInitialization: 初始化前
 * postProcessAfterInitialization: 初始化后
 *
 * @author maoyz0621
 */
public class StartTimeMetric implements BeanPostProcessor, InstantiationAwareBeanPostProcessor, PriorityOrdered, ApplicationListener<ContextRefreshedEvent>, MergedBeanDefinitionPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(StartTimeMetric.class);

    private final Map<String, Statistics> statisticsMap = Maps.newConcurrentMap();

    private static final AtomicBoolean START_LOCK = new AtomicBoolean(false);


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        String beanClassName = bean.getClass().getName();
        Statistics statistics = statisticsMap.getOrDefault(beanClassName, Statistics.builder().beanName(beanClassName).build());
        statistics.setBeforeInitializationTime(System.currentTimeMillis());
        statisticsMap.putIfAbsent(beanClassName, statistics);
        return bean;

    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        String beanClassName = bean.getClass().getName();
        Statistics statistics = statisticsMap.get(beanClassName);
        if (statistics != null) {
            statistics.setAfterInitializationTime(System.currentTimeMillis());
        }
        return bean;

    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        String beanClassName = beanClass.getName();
        Statistics statistics = Statistics.builder().beanName(beanClassName).build();
        statistics.setBeforeInstantiationTime(System.currentTimeMillis());
        statisticsMap.put(beanClassName, statistics);
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        String beanClassName = bean.getClass().getName();
        Statistics statistics = statisticsMap.get(beanClassName);
        if (statistics != null) {
            statistics.setAfterInstantiationTime(System.currentTimeMillis());
        }
        return true;
    }

    /**
     * 调整当前 BeanPostProcessor的执行顺序到最后
     *
     * @param beanDefinition the merged bean definition for the bean
     * @param beanType       the actual type of the managed bean instance
     * @param beanName       the name of the bean
     */
    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Spring 容器启动完成");
        if (START_LOCK.compareAndSet(false, true)) {
            List<Statistics> statisticsList = statisticsMap.values().stream()
                    .sorted(Comparator.comparing(Statistics::calculateTotalCostTime).reversed())
                    .collect(Collectors.toList());

            StringBuilder sb = new StringBuilder();
            statisticsList.forEach(statistics -> sb.append(statistics.toConsoleString()));
            logger.info("ApplicationStartupTimeMetric:\n" + sb);
        }
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE;
    }

}