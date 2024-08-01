package com.myz.springboot2.monitor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author maoyz0621
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Statistics {
    private String beanName;

    private long beforeInstantiationTime;

    private long afterInstantiationTime;

    private long beforeInitializationTime;

    private long afterInitializationTime;

    public long calculateTotalCostTime() {
        return calculateInstantiationCostTime() + calculateInitializationCostTime();
    }

    public long calculateInstantiationCostTime() {
        return afterInstantiationTime - beforeInstantiationTime;
    }

    public long calculateInitializationCostTime() {
        return afterInitializationTime - beforeInitializationTime;
    }

    public String toConsoleString() {
        return "\t" + beanName + "\t" + calculateTotalCostTime() + "\t\n";
    }

}