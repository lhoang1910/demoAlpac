package com.demo.demo.service;

import com.demo.demo.dto.request.CreateCustomerRequest;
import com.demo.demo.dto.response.WrapResponse;


public interface CustomerService {
    WrapResponse<?> createCustomer(CreateCustomerRequest request);

    WrapResponse<?> findAllBeNotDeleteCustomer();

    WrapResponse<?> deleteCustomerById(String id);

    WrapResponse<?> updateCustomerById(String id, CreateCustomerRequest request);
}
