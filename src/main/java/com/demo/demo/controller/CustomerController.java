package com.demo.demo.controller;

import com.demo.demo.dto.request.CreateCustomerRequest;
import com.demo.demo.service.CustomerService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllCustomer(){
        return ResponseEntity.ok(customerService.findAllBeNotDeleteCustomer());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.ok(customerService.updateCustomerById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return ResponseEntity.ok(customerService.deleteCustomerById(id));
    }

}
