/**
 * Copyright 2019 asiainfo Inc.
 **/
package com.myz.swagger.common.module;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author maoyz0621 on 19-1-12
 * @version: v1.0
 */
@Setter
@Getter
@NoArgsConstructor
public class JsonResult<T> {

    private String status = null;

    private String message;

    private T result = null;
}
