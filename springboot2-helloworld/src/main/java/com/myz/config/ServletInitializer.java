package com.myz.config;

import com.example.app.SpringBootHelloWorldApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 需要启动类继承自SpringBootServletInitializer方可正常部署至常规tomcat下，其主要能够起到web.xml的作用
 * @author maoyz0621
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootHelloWorldApplication.class);
    }

}
