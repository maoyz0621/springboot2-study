/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-07-10 15:01  Inc. All rights reserved.
 */
package com.myz.springboot2.common.data;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author maoyz
 */
public class MultiResult<T> extends BaseResult {
    private static final long serialVersionUID = 1L;
    private int total;
    private Collection<T> data;

    public Collection<T> getData() {
        return data == null ? new ArrayList<>() : new ArrayList<>(data);
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public static <T> MultiResult<T> of(Collection<T> data) {
        MultiResult result = new MultiResult();
        result.setCode(0);
        result.setMessage("success");
        result.setData(data);
        return result;
    }

    public static MultiResult buildSuccess() {
        MultiResult result = new MultiResult();
        result.setCode(0);
        result.setMessage("success");
        return result;
    }


    public static MultiResult buildFailure() {
        MultiResult result = new MultiResult();
        result.setCode(-1);
        result.setMessage("");
        return result;
    }

    public static MultiResult buildFailure(int code, String message) {
        MultiResult result = new MultiResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}