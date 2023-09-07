package com.myz.commandline.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 接口的 Component 会在所有 Spring Beans 都初始化之后，SpringApplication.run() 之前执行，非常适合在应用程序启动之初进行一些数据初始化的工作
 * CommandLineRunner、ApplicationRunner 接口是在容器启动成功后的最后一步回调（类似开机自启动）。
 * @author maoyz
 */
@Component
@Slf4j
@Order(1)
public class SystemCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        // 启动参数：-Dmyz=1
        String myz = System.getProperty("myz", "123");
        System.out.println(myz);
        log.info("***************** CommandLineRunner *******************");
    }
}
