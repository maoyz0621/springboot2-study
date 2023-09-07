/**
 * Copyright 2023 Inc.
 **/
package com.myz.springboot2.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ┌─────┐
 * |  cycleC (field private com.myz.springboot2.cycle.CycleD com.myz.springboot2.cycle.CycleC.cycleD)
 * ↑     ↓
 * |  cycleD (field com.myz.springboot2.cycle.CycleC com.myz.springboot2.cycle.CycleD.cycleC)
 *
 * @author maoyz0621 on 2023/8/22
 * @version v1.0
 */
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CycleD {

    @Autowired
    CycleC cycleC;
}