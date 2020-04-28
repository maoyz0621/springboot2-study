/**
 * Copyright 2020 Inc.
 **/
package com.myz.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz0621 on 20-4-22
 * @version v1.0
 */
@SpringBootApplication(scanBasePackages = {"com.myz.job.bean.spring"})
public class XxlJobExecutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(XxlJobExecutorApplication.class, args);
    }
}
