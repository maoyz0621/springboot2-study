/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2.mybatis.encrypt.utils;

import org.springframework.lang.Nullable;

/**
 * @author maoyz0621 on 2020/11/9
 * @version v1.0
 */
public class ExceptionUtil {

    public static RuntimeException unchecked(@Nullable Throwable t) {
        if (t instanceof RuntimeException) {
            throw (RuntimeException) t;
        }
        if (t instanceof Error) {
            throw (Error) t;
        }

        throw new UncheckedException(t);
    }
}