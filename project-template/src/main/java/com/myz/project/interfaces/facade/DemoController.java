/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-01 18:09  Inc. All rights reserved.
 */
package com.myz.project.interfaces.facade;

import com.alibaba.cola.dto.SingleResponse;
import com.myz.project.domain.pojo.vo.AnimalReq;
import com.myz.project.domain.repository.db.Refrigerator;
import com.myz.project.domain.service.IAnimalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IAnimalCenterService IAnimalCenterService;

    @PostMapping("/put")
    public SingleResponse<Refrigerator> put(@Validated @RequestBody AnimalReq animalReq) {
        return IAnimalCenterService.put(animalReq);
    }
}