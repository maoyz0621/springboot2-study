/**
 * Copyright 2022 Inc.
 **/
package com.myz.healthcheck.client.base;

import java.util.Map;

/**
 * @author maoyz0621 on 2022/5/8
 * @version v1.0
 */
public final class Health {

    private final Status status;
    private final Map<String, Object> details;

    public Health(Map<String, Object> details, Status status) {
        this.details = details;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public Map<String, Object> getDetails() {
        return details;
    }
}