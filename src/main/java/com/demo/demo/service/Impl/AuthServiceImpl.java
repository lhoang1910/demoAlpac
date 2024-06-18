package com.demo.demo.service.Impl;

import com.demo.demo.component.constant.HttpStatusCodes;
import com.demo.demo.component.constant.UserRole;
import com.demo.demo.dto.request.CreateUserRequest;
import com.demo.demo.dto.request.LoginRequest;
import com.demo.demo.dto.response.LoginSuccessResponse;
import com.demo.demo.dto.response.WrapResponse;
import com.demo.demo.entity.UserEntity;
import com.demo.demo.repository.UserRepository;
import com.demo.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Override
    public WrapResponse<?> createUser(CreateUserRequest request) {
        Optional<UserEntity> existUser = userRepository.findByUsername(request.getUsername());

        if (existUser.isPresent()){
            return new WrapResponse<>(false, HttpStatusCodes.CONFLICT, "Tên đăng nhập đã tồn tại");
        }

        UserEntity newUser= new UserEntity();
        newUser.setId(String.valueOf(UUID.randomUUID()));
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(UserRole.ROLE_NHAN_VIEN);
        newUser.setDeleted(false);

        long numOfUser = userRepository.quantityOfUser() + 1;
        String userCode = String.format("U%03d",numOfUser);

        newUser.setUserCode(userCode);
        userRepository.save(newUser);

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Đăng ký thành công");
    }

    @Override
    public WrapResponse<LoginSuccessResponse> login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<UserEntity> loginedUser = userRepository.findByUsername(request.getUsername());

        if (loginedUser.isEmpty()){
            return new WrapResponse<>(false, HttpStatusCodes.NOT_FOUND, "Tài khoản không tồn tại");
        }

        LoginSuccessResponse response = new LoginSuccessResponse();

        response.setUsername(loginedUser.get().getUsername());
        response.setUserCode(loginedUser.get().getUserCode());
        response.setRole(response.setRoleName(loginedUser.get().getRole()));
        response.setDeleted(loginedUser.get().isDeleted());

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Đăng nhập thành công", response);
    }
}
