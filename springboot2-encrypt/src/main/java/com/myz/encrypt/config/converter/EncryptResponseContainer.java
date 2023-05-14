/**
 * Copyright 2023 Inc.
 **/
package com.myz.encrypt.config.converter;

import com.myz.encrypt.config.annotation.EncryptResponse;

/**
 * @author maoyz0621 on 2023/5/11
 * @version v1.0
 */
public class EncryptResponseContainer {

    private Object value;

    private EncryptResponse encryptResponse;

    public EncryptResponseContainer(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public EncryptResponse getEncryptResponse() {
        return encryptResponse;
    }

    public void setEncryptResponse(EncryptResponse encryptResponse) {
        this.encryptResponse = encryptResponse;
    }
}