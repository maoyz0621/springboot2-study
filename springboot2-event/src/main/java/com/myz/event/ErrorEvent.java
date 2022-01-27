/**
 * Copyright 2022 Inc.
 **/
package com.myz.event;

/**
 * @author maoyz0621 on 2022/1/23
 * @version v1.0
 */
public class ErrorEvent<T> extends BaseEvent<T>{

    public ErrorEvent(T source) {
        super(source);
    }
}