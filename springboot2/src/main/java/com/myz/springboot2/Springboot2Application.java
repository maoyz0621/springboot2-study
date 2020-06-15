package com.myz.springboot2;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 扫描mapper @MapperScan(basePackages = {"com.myz.springboot2.mapper"})
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "com.myz.springboot2.repository")
@EnableCaching
//@MapperScan(basePackages = {"com.myz.springboot2.mapper"})
public class Springboot2Application {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2Application.class, args);
    }

    @Bean
    public DataLoader dataLoader() {
        return new DataLoader();
    }

    /**
     * CommandLineRunner
     */
    static class DataLoader implements CommandLineRunner {

        @Override
        public void run(String... args) throws Exception {
            log.info("Loading data..." + args);
        }
    }
}
