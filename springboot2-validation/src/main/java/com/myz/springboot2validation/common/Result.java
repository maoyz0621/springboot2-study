/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.springboot2validation.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author maoyz0621 on 19-1-10
 * @version: v1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -7676953430588237941L;

    private boolean success;
    private int code;
    private String message;
    private T content;
}
