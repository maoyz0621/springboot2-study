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
public class CycleB {

    // 1、通过构造方法依赖注入时产生循环依赖
    // private final CycleA cycleA;
    //
    // public CycleB(CycleA cycleA) {
    //     this.cycleA = cycleA;
    // }


    // 2、解决方法：通过setter方法注入
    private CycleA cycleA;

    public void setCycleA(CycleA cycleA) {
        this.cycleA = cycleA;
    }


    // 3、解决方法：通过构造方法依赖注入时延迟加载 @Lazy
    // private final CycleA cycleA;
    //
    // @Lazy
    // public CycleB(CycleA cycleA) {
    //     this.cycleA = cycleA;
    // }
}