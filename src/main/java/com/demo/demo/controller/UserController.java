package com.demo.demo.controller;

import com.demo.demo.dto.request.CreateUserRequest;
import com.demo.demo.dto.response.MessageResponse;
import com.demo.demo.repository.UserRepository;
import com.demo.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable String id) {
        MessageResponse response = userService.deleteUserById(id);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserById(@Valid @PathVariable String id, @RequestBody @Valid CreateUserRequest createUserRequest) {
        MessageResponse response = userService.updateUserById(id, createUserRequest);
        if (response != null) {
            return ResponseEntity.ok(response.getMessage());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng với ID: " + id);
        }
    }

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok(userRepository.findAll());
    }
}
