/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2error.service;

import com.myz.springboot2.common.exception.BusinessException;
import org.springframework.stereotype.Service;

/**
 * @author maoyz0621 on 20-4-22
 * @version v1.0
 */
@Service
public class ErrorService {

    // 抛出自定义异常
    public int test(int i) {
        try {
            return 1 / i;
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
