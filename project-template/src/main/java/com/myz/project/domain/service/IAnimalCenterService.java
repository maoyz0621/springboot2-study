/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-02 10:14  Inc. All rights reserved.
 */
package com.myz.project.domain.service;

import com.alibaba.cola.dto.SingleResponse;
import com.myz.project.domain.pojo.vo.AnimalReq;
import com.myz.project.domain.repository.db.Refrigerator;

/**
 * @author maoyz
 */
public interface IAnimalCenterService {
    SingleResponse<Refrigerator> put(AnimalReq animalReq);
}