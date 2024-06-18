package com.demo.demo.controller;

import com.demo.demo.dto.request.CreateContractRequest;
import com.demo.demo.repository.ContractRepository;
import com.demo.demo.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/contract")
public class ContractController {

    @Autowired
    ContractService contractService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid  @RequestBody CreateContractRequest request){
        try {
            return ResponseEntity.ok(contractService.createContract(request));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> changeTheContractToExpire(@PathVariable String id){
        return ResponseEntity.ok(contractService.cancelContract(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid  @RequestBody CreateContractRequest request){
        try {
            return ResponseEntity.ok(contractService.updateContract(id, request));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        return ResponseEntity.ok(contractService.deleteContractByContractId(id));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> get(@PathVariable String id){
        return null;
    }
}
