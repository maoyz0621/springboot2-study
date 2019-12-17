package com.myz.maoyz.spring.boot.autoconfigure;

import com.myz.maoyz.spring.boot.autoconfigure.bean.HaveBean;
import com.myz.maoyz.spring.boot.autoconfigure.bean.HaveClass;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BeforeStarterAutoConfiguration加载完成之后再加载 @AutoConfigureAfter
 *
 * @author maoyz
 */
@Configuration
@EnableConfigurationProperties(value = MaoyzProperties.class)
@AutoConfigureAfter({BeforeStarterAutoConfiguration.class})
public class MaoyzStarterAutoConfiguration {

    private final MaoyzProperties properties;

    public MaoyzStarterAutoConfiguration(MaoyzProperties properties) {
        this.properties = properties;
    }

    /**
     * 说明: @ConditionalOnClass依赖中包含HaveClass.class, 当类路径下有指定的类的条件下
     */
    @Bean
    @ConditionalOnClass(HaveClass.class)
    public MaoyzService maoyzServiceHaveClass() {
        System.out.println("**************** ConditionalOnClass *****************");
        return new MaoyzService();
    }

    /**
     * 说明: @ConditionalOnBean, 项目中注入HaveBean这个bean时, 执行; 当容器里有指定的Bean的条件下
     */
    @Bean
    @ConditionalOnBean(HaveBean.class)
    public MaoyzService maoyzServiceHaveBean() {
        System.out.println("**************** ConditionalOnBean *****************");
        return new MaoyzService();
    }

    /**
     * 说明: @ConditionalOnMissingClass依赖中不包含HaveClass.class, 当类路径下没有指定的类的条件下
     */
    @Bean
    @ConditionalOnMissingClass("com.myz.maoyz.spring.boot.autoconfigure.bean.MissingClass")
    public MaoyzService maoyzServiceMissingClass() {
        System.out.println("**************** ConditionalOnMissingClass *****************");
        return new MaoyzService();
    }

    /**
     * 说明: @ConditionalOnMissingBean, 项目中没有注入HaveBean这个bean时, 执行; 当容器里没有指定的Bean的条件下
     */
    @Bean
    @ConditionalOnMissingBean(HaveBean.class)
    public MaoyzService maoyzServiceMissingBean() {
        System.out.println("**************** ConditionalOnMissingBean *****************");
        return new MaoyzService();
    }

}
