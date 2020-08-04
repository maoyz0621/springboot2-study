package com.myz.commandline;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 接口的 Component 会在所有 Spring Beans 都初始化之后，SpringApplication.run() 之前执行，非常适合在应用程序启动之初进行一些数据初始化的工作
 *
 * @author maoyz
 */
@Component
@Slf4j
@Order(1)
public class SystemRunner implements CommandLineRunner {

    public void run(String... args) throws Exception {
        // 启动参数：-Dmyz=1
        String myz = System.getProperty("myz", "123");
        System.out.println(myz);
        log.info("***************** CommandLineRunner *******************");
    }
}
