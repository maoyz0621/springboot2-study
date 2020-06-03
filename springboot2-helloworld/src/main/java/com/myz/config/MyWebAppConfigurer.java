package com.myz.config;

import com.myz.interceptor.BaseInterceptor;
import com.myz.interceptor.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * springmvc 配置文件注册器
 * 采用JavaBean的形式来代替传统的xml配置文件形式进行针对框架个性化定制
 * 开启SpringMVC功能@EnableWebMvc
 *
 * @author maoyz on 18-3-11.
 */
@Configuration
@EnableWebMvc
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 定义拦截器InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new BaseInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    // /**
    //  * 配置视图解析
    //  * @param registry
    //  */
    // @Override
    // public void configureViewResolvers(ViewResolverRegistry registry) {
    //   registry.jsp("/WEB-INF/views","jsp");
    //   registry.freeMarker();
    // }
    //
    // /**
    //  * 添加视图解析器
    //  * @param registry
    //  */
    // @Override
    // public void addViewControllers(ViewControllerRegistry registry) {
    //   registry.addViewController("");
    //   super.addViewControllers(registry);
    // }
}
