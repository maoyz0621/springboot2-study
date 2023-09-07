package com.myz.commandline;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author maoyz
 */
@SpringBootApplication
@Slf4j
public class CommandLineRunnerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandLineRunnerApplication.class, args);
        log.info("================== start success ==================");
    }
}
