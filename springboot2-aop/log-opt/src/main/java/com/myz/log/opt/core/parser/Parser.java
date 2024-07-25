/**
 * Copyright 2024 Inc.
 **/
package com.myz.log.opt.core.parser;

/**
 * @author maoyz0621 on 2024/7/25
 * @version v1.0
 */
public interface Parser<T> {

    /**
     * @param target parsable target
     * @return the parsed value
     */
    Object parse(T target);

}
