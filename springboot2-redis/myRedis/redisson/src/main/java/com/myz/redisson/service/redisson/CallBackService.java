/**
 * Copyright 2019 Inc.
 **/
package com.myz.redisson.service.redisson;

/**
 * @author maoyz0621 on 19-7-31
 * @version: v1.0
 */
public abstract class CallBackService<T> {

    /**
     * 执行函数
     * @return T
     */
    public abstract T exec();

    Object object;

    public CallBackService() {
        super();
    }

    public CallBackService(Object object) {
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

}
