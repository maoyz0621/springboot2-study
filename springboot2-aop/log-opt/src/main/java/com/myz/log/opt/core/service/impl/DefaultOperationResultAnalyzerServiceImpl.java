/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.service.impl;

import com.myz.log.opt.core.service.OperationResultAnalyzerService;

import java.util.Objects;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public class DefaultOperationResultAnalyzerServiceImpl implements OperationResultAnalyzerService {
    @Override
    public boolean analyzeOperationResult(Throwable throwable, Object result) {
        return Objects.isNull(throwable);
    }
}