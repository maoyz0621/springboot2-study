/**
 * Copyright 2023 Inc.
 **/
package com.myz.springboot2.jackson.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author maoyz0621 on 2023/12/27
 * @version v1.0
 */
@Data
public class SingleBean implements Serializable {

    private static final long serialVersionUID = -7955189083381881185L;

    private String sType;

    @JsonProperty("tHeight")
    private String tHeight;

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

}