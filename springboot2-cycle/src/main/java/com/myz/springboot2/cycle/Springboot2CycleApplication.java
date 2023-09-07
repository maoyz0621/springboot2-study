/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The dependencies of some of the beans in the application context form a cycle:
 *
 *    springboot2CycleApplication (field private com.myz.springboot2.cycle.CycleA com.myz.springboot2.cycle.Springboot2CycleApplication.cycleA)
 * ┌─────┐
 * |  cycleA defined in file [F:\Work\IDEA\springboot2-study\springboot2-cycle\target\classes\com\myz\springboot2\cycle\CycleA.class]
 * ↑     ↓
 * |  cycleB defined in file [F:\Work\IDEA\springboot2-study\springboot2-cycle\target\classes\com\myz\springboot2\cycle\CycleB.class]
 * └─────┘
 * @author maoyz0621 on 2022/8/20
 * @version v1.0
 */
@SpringBootApplication
public class Springboot2CycleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2CycleApplication.class, args);
    }

    @Autowired
    private CycleA cycleA;

    @Autowired
    private CycleB cycleB;

    @Autowired
    private CycleC cycleC;

    @Autowired
    private CycleD cycleD;
}