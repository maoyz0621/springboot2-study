package com.myz.ddd.project.domain.gateway;

import com.myz.ddd.project.domain.customer.Customer;

public interface CustomerGateway {
    public Customer getByById(String customerId);
}
