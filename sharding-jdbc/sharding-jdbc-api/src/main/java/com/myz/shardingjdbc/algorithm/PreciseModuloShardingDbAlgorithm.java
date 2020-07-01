/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-24 16:35  Inc. All rights reserved.
 */
package com.myz.shardingjdbc.algorithm;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 精确分片算法
 *
 * @author maoyz
 */
public class PreciseModuloShardingDbAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        for (String name : availableTargetNames) {
            if (name.endsWith(shardingValue.getValue() % 2 + "")) {
                return name;
            }
        }
        return null;
    }
}