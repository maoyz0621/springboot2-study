/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-02 10:17  Inc. All rights reserved.
 */
package com.myz.project.domain.pojo.vo;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author maoyz
 */
@AllArgsConstructor
@NoArgsConstructor
public class AnimalReq {

    @NotEmpty
    @Size(max = 6)
    @Setter
    @Getter
    private String name;
}