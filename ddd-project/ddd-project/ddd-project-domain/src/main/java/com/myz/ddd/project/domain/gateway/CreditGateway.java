package com.myz.ddd.project.domain.gateway;

import com.myz.ddd.project.domain.customer.Customer;
import com.myz.ddd.project.domain.customer.Credit;

//Assume that the credit info is in antoher distributed Service
public interface CreditGateway {
    public Credit getCredit(String customerId);
}
