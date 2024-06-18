package com.demo.demo.controller;

import com.demo.demo.dto.request.CreateContractRequest;
import com.demo.demo.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/internal/api/contract")
public class InternalContractController {

    @Autowired
    ContractService contractService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> get(@PathVariable String id){
        return null;
    }
}
