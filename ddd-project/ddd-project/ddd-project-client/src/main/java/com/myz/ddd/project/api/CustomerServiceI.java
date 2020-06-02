package com.myz.ddd.project.api;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.myz.ddd.project.dto.CustomerAddCmd;
import com.myz.ddd.project.dto.CustomerListByNameQry;
import com.myz.ddd.project.dto.domainmodel.Customer;

public interface CustomerServiceI {

    public Response addCustomer(CustomerAddCmd customerAddCmd);

    public MultiResponse<Customer> listByName(CustomerListByNameQry customerListByNameQry);
}
