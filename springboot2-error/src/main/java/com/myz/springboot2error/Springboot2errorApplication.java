package com.myz.springboot2error;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 添加skywalking启动参数
 * <p>
 * -javaagent:E:\WorkWare\Apache-skywalking\apache-skywalking-apm-bin\agent\skywalking-agent.jar
 * -Dskywalking.agent.service_name=jiangsu-bid-service
 * -Dskywalking.collector.backend_service=192.168.107.110:11800
 */
@SpringBootApplication
public class Springboot2errorApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2errorApplication.class, args);
    }

}
