package com.demo.demo.service;

import com.demo.demo.dto.request.CreateCustomerRequest;
import com.demo.demo.dto.response.CustomerResponse;
import com.demo.demo.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CreateCustomerRequest request);

    List<CustomerEntity> findAllVietNamCountryCustomer ();
}
