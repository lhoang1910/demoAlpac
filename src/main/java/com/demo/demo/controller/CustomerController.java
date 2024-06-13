package com.demo.demo.controller;

import com.demo.demo.dto.request.CreateCustomerRequest;
import com.demo.demo.repository.CustomerRepository;
import com.demo.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/add")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @GetMapping("/all-customer")
    public ResponseEntity<?> getAllCustomer(){
        return ResponseEntity.ok(customerService.findAllVietNamCountryCustomer());
    }

    @GetMapping("/user-by-id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        return ResponseEntity.ok(customerRepository.findById(id));
    }
}
