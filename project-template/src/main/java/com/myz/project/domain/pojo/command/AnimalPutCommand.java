/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-02 10:47  Inc. All rights reserved.
 */
package com.myz.project.domain.pojo.command;


import com.alibaba.cola.dto.Command;
import lombok.*;

/**
 * @author maoyz
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalPutCommand extends Command {

    private String name;
}