package com.demo.demo.service.Impl;

import com.demo.demo.dto.request.CreateUserRequest;
import com.demo.demo.dto.response.MessageResponse;
import com.demo.demo.entity.UserEntity;
import com.demo.demo.repository.UserRepository;
import com.demo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${role.NHAN_VIEN}")
    int ROLE_NHAN_VIEN;

    @Value("${role.TRUONG_PHONG}")
    int ROLE_TRUONG_PHONG;

    @Value("${role.PHO_PHONG}")
    int ROLE_PHO_PHONG;

    @Value("${role.ADMIN}")
    int ROLE_ADMIN;

    @Override
    public MessageResponse deleteUserById(String id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (user.isDeleted()){
                return new MessageResponse("Người dùng không tồn tại", true);
            }
            user.setDeleted(true);
            userRepository.save(user);
            return new MessageResponse("Xóa người dùng thành công", true);
        } else {
            return new MessageResponse("Không tìm thấy người dùng với ID: " + id, false);
        }
    }

    @Override
    public MessageResponse updateUserById(String id, CreateUserRequest createUserRequest) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return new MessageResponse("Không tìm thấy người dùng với ID: " + id, false);
        }
        UserEntity user = optionalUser.get();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        if (createUserRequest.getRole() != ROLE_ADMIN) {
            user.setRole(createUserRequest.getRole());
        } else {
            return new MessageResponse("Không được chuyển đổi tài khoản này thành tài khoản admin", false);
        }
        userRepository.save(user);
        return new MessageResponse("Cập nhật người dùng thành công", true);
    }
}
