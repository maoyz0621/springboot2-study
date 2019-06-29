package com.myz.springboot2elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * 开启elasticSearch扫包 @EnableElasticsearchRepositories(basePackages = {"com.myz.springboot2elasticsearch.dao"})
 */
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = {"com.myz.springboot2elasticsearch.dao"})
public class Springboot2ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2ElasticsearchApplication.class, args);
    }

}

