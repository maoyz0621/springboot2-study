/**
 * Copyright 2018 asiainfo Inc.
 **/
package com.myz.springboot2.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author maoyz on 2018/8/26
 * @version: v1.0
 */
public class WebMvcConfig implements WebMvcConfigurer {

  // @Override
  // public void addResourceHandlers(ResourceHandlerRegistry registry) {
  //   registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
  //   registry.addResourceHandler("swagger-ui.html")
  //           .addResourceLocations("classpath:/META-INF/resources/");
  //   registry.addResourceHandler("/webjars/**")
  //           .addResourceLocations("classpath:/META-INF/resources/webjars/");
  // }
}
