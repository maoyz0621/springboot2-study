/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-01 18:09  Inc. All rights reserved.
 */
package com.myz.project.controller;

import com.alibaba.cola.dto.SingleResponse;
import com.myz.project.pojo.vo.AnimalReq;
import com.myz.project.repository.db.domain.Refrigerator;
import com.myz.project.service.AnimalCenterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller 层完成对数据的检查,调用Service层
 *
 * @author maoyz
 */
@RestController
public class DemoController {

    private AnimalCenterService animalCenterService;

    @PostMapping("/put")
    public SingleResponse<Refrigerator> put(@Validated @RequestBody AnimalReq animalReq) {
        return animalCenterService.put(animalReq);
    }
}