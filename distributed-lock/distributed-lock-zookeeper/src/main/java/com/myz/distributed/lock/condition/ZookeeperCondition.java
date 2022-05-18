/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author maoyz0621 on 2022/5/10
 * @version v1.0
 */
public class ZookeeperCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        return environment.containsProperty("spring.coordinate.zookeeper.servers")
                || environment.containsProperty("spring.coordinate.zookeeper.zk-servers");
    }
}