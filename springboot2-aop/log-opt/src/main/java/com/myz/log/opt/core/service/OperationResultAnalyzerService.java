/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.service;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public interface OperationResultAnalyzerService {

    boolean analyzeOperationResult(Throwable throwable, Object result);
}