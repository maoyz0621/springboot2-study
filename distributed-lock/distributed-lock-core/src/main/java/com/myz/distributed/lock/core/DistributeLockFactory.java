/**
 * Copyright 2022 Inc.
 **/
package com.myz.distributed.lock.core;

import com.myz.distributed.lock.core.config.DistributedLockProperties;
import com.myz.distributed.lock.core.executor.DistributedLockExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author maoyz0621 on 2022/5/22
 * @version v1.0
 */
@Slf4j
@RequiredArgsConstructor
public class DistributeLockFactory implements InitializingBean {

    private Map<Class<? extends DistributedLockExecutor>, DistributedLockExecutor> distributedLockExecutorMap = new ConcurrentHashMap<>();
    private final List<DistributedLockExecutor> distributedLockExecutors;

    private final DistributedLockProperties distributedLockProperties;

    private DistributedLockExecutor primaryExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(distributedLockExecutors)) {
            log.warn("DistributeLockFactory has no DistributedLockExecutor");
            return;
        }
        distributedLockExecutorMap = distributedLockExecutors.stream()
                .collect(Collectors.toConcurrentMap(DistributedLockExecutor::getClass, Function.identity(), (k1, k2) -> k2));

        final Class<? extends DistributedLockExecutor> primaryExecutorClass = distributedLockProperties.getPrimaryExecutor();
        if (primaryExecutorClass == null) {
            this.primaryExecutor = distributedLockExecutors.get(0);
        } else {
            this.primaryExecutor = distributedLockExecutorMap.get(primaryExecutorClass);
            Assert.notNull(this.primaryExecutor, "primaryExecutor must be not null");
        }
    }

    public DistributedLockExecutor getExecutor(Class<? extends DistributedLockExecutor> distributedLockExecutor) {
        DistributedLockExecutor executor = null;
        if (distributedLockExecutor == null || distributedLockExecutor == DistributedLockExecutor.class) {
            return this.primaryExecutor;
        } else {
            return distributedLockExecutorMap.get(distributedLockExecutor);
        }
    }
}