package com.demo.demo.service;

import com.demo.demo.dto.request.CreateContractRequest;
import com.demo.demo.dto.response.WrapResponse;

import java.io.IOException;

public interface ContractService {
    WrapResponse<?> createContract(CreateContractRequest request) throws IOException;
    WrapResponse<?> updateContract(String id, CreateContractRequest request) throws IOException;
    WrapResponse<?> deleteContractByContractId(String id);
    WrapResponse<?> cancelContract(String id);
    void trackingContractStatus();
}
