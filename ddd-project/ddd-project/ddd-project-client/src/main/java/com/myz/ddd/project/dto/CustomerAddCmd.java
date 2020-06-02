package com.myz.ddd.project.dto;

import com.alibaba.cola.dto.Command;
import com.myz.ddd.project.dto.domainmodel.Customer;
import lombok.Data;

@Data
public class CustomerAddCmd extends Command{

    private Customer customer;

}
