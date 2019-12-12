package com.myz.springboot2.starter.config;

import com.myz.maoyz.spring.boot.autoconfigure.bean.HaveBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author maoyz
 */
@Configuration
public class StarterConfigurate {

    /**
     * 加@Bean, 会执行 @ConditionalOnBean(HaveBean.class)
     * 不加@Bean, 会执行 @ConditionalOnMissingBean(HaveBean.class)
     */
    // @Bean
    public HaveBean haveBean() {
        return new HaveBean();
    }
}
