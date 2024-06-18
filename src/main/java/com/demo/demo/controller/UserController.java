package com.demo.demo.controller;

import com.demo.demo.dto.request.CreateUserRequest;
import com.demo.demo.dto.request.FilterRequest;
import com.demo.demo.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody List<CreateUserRequest> request){
        return ResponseEntity.ok(userService.saveAllUser(request));
    }

    @GetMapping("/")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(userService.updateUserById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @GetMapping("/user-by-page")
    public ResponseEntity<?> getAllUsersByPage(@RequestBody FilterRequest request) {
        return ResponseEntity.ok(userService.findAllUsers(request));
    }

}
