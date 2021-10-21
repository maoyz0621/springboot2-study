package com.myz.springboot2elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;

/**
 * 开启elasticSearch扫包 @EnableElasticsearchRepositories(basePackages = {"com.myz.springboot2elasticsearch.dao"})
 *
 * @author maoyz0621
 */
@Slf4j
@SpringBootApplication(exclude = {ElasticsearchDataAutoConfiguration.class})
@EnableElasticsearchRepositories(basePackages = {"com.myz.springboot2elasticsearch.repository"})
public class Springboot2ElasticsearchApplication {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) {
        final StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            SpringApplication.run(Springboot2ElasticsearchApplication.class, args);
            countDownLatch.await();
        } catch (Exception e) {
            log.error("Springboot2ElasticsearchApplication start error,", e);
        }
        stopWatch.stop();
        log.info("Springboot2ElasticsearchApplication start success {}", stopWatch.getTotalTimeMillis());
    }

}

