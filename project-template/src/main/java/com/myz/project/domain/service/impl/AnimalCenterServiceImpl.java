/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-02 10:15  Inc. All rights reserved.
 */
package com.myz.project.domain.service.impl;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.SingleResponse;
import com.myz.project.domain.pojo.command.AnimalPutCommand;
import com.myz.project.domain.pojo.vo.AnimalReq;
import com.myz.project.domain.repository.db.Refrigerator;
import com.myz.project.domain.service.IAnimalCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author maoyz
 */
@Service
public class AnimalCenterServiceImpl implements IAnimalCenterService {

    @Autowired
    private CommandBusI commandBus;

    @Override
    public SingleResponse<Refrigerator> put(AnimalReq animalReq) {
        AnimalPutCommand animalPutCommand = new AnimalPutCommand(animalReq.getName());
        return (SingleResponse<Refrigerator>) commandBus.send(animalPutCommand);
    }
}