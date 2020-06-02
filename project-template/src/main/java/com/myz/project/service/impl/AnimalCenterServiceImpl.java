/*
 * Copyright (C) 2020, All rights Reserved, Designed By
 * @author: maoyz
 * @Copyright: 2020-06-02 10:15  Inc. All rights reserved.
 */
package com.myz.project.service.impl;

import com.alibaba.cola.command.CommandBusI;
import com.alibaba.cola.dto.SingleResponse;
import com.myz.project.pojo.command.AnimalPutCommand;
import com.myz.project.pojo.vo.AnimalReq;
import com.myz.project.repository.db.domain.Refrigerator;
import com.myz.project.service.AnimalCenterService;
import org.springframework.stereotype.Service;

/**
 * @author maoyz
 */
@Service
public class AnimalCenterServiceImpl implements AnimalCenterService {

    private CommandBusI commandBus;
    @Override
    public SingleResponse<Refrigerator> put(AnimalReq animalReq) {
        AnimalPutCommand animalPutCommand = new AnimalPutCommand(animalReq.getName());
        return commandBus.send(animalPutCommand);
    }
}