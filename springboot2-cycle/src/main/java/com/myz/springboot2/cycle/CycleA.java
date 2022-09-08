/**
 * Copyright 2022 Inc.
 **/
package com.myz.springboot2.cycle;

import org.springframework.stereotype.Component;

/**
 * @author maoyz0621 on 2022/8/20
 * @version v1.0
 */
@Component
public class CycleA {

    private final CycleB cycleB;

    public CycleA(CycleB cycleB) {
        this.cycleB = cycleB;
    }

}