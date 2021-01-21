// /**
//  * Copyright 2021 Inc.
//  **/
// package com.myz.springboot2elasticsearch.config.highclient;
//
// import org.elasticsearch.client.RestHighLevelClient;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.autoconfigure.elasticsearch.rest.RestClientProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.data.elasticsearch.client.ClientConfiguration;
// import org.springframework.data.elasticsearch.client.RestClients;
// import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
// import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
// import org.springframework.stereotype.Component;
//
// import javax.annotation.Resource;
//
// /**
//  * @author maoyz0621 on 2021/1/7
//  * @version v1.0
//  */
// @Component
// @Configuration
// @EnableElasticsearchRepositories
// public class RestClientConfig extends AbstractElasticsearchConfiguration {
//
//     @Value("${spring.elasticsearch.rest.uris}")
//     private String uris;
//
//     @Resource
//     RestClientProperties restClientProperties;
//
//     @Override
//     @Bean
//     public RestHighLevelClient elasticsearchClient() {
//         System.out.println(restClientProperties);
//         System.out.println(uris);
//         final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                 .connectedTo("localhost:9200")
//                 .build();
//
//         return RestClients.create(clientConfiguration).rest();
//     }
// }