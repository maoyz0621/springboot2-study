/**
 * Copyright 2020 Inc.
 **/
package com.myz.springboot2validation.common.model;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * @author maoyz0621 on 2020/9/18
 * @version v1.0
 */
@Data
public class ListRequestParam<T> {

    @Valid
    private List<T> list;

}