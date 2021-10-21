/**
 * Copyright 2021 Inc.
 **/
package com.myz.springboot2elasticsearch.config.highclient;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * @author maoyz0621 on 2021/1/7
 * @version v1.0
 */
@Configuration
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        return this.restHighLevelClient;
    }

    @Bean
    public ElasticsearchRestTemplate elasticsearchRestTemplate(RestHighLevelClient restHighLevelClient) {
        return new ElasticsearchRestTemplate(restHighLevelClient);
    }

}