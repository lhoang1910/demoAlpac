package com.demo.demo.service.Impl;

import com.demo.demo.component.constant.HttpStatusCodes;
import com.demo.demo.component.constant.UserRole;
import com.demo.demo.dto.request.CreateUserRequest;
import com.demo.demo.dto.request.FilterRequest;
import com.demo.demo.dto.response.WrapResponse;
import com.demo.demo.entity.UserEntity;
import com.demo.demo.repository.UserRepository;
import com.demo.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public WrapResponse<?> deleteUserById(String id) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return new WrapResponse<>(false, HttpStatusCodes.NOT_FOUND, "Không tìm thấy người dùng với ID: " + id);
        }

        UserEntity user = optionalUser.get();
        if (user.isDeleted()) {
            return new WrapResponse<>(false, HttpStatusCodes.METHOD_NOT_ALLOWED, "Người dùng đang ở trạng thái đã xóa");
        }

        user.setDeleted(true);
        userRepository.save(user);
        return new WrapResponse<>(true, HttpStatusCodes.OK, "Xóa người dùng thành công");

    }

    @Override
    public WrapResponse<?> updateUserById(String id, CreateUserRequest createUserRequest) {
        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return new WrapResponse<>(false, HttpStatusCodes.NOT_FOUND, "Không tìm thấy người dùng với ID: " + id);
        }
        UserEntity user = optionalUser.get();
        user.setUsername(createUserRequest.getUsername());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));

        if (createUserRequest.getRole() != UserRole.ROLE_ADMIN) {
            user.setRole(createUserRequest.getRole());
        } else {
            return new WrapResponse<>(false, HttpStatusCodes.METHOD_NOT_ALLOWED, "Không được chuyển đổi tài khoản này thành tài khoản admin");
        }
        userRepository.save(user);
        return new WrapResponse<>(true, HttpStatusCodes.OK, "Cập nhật người dùng thành công", user);
    }

    @Override
    public WrapResponse<?> getAllUser() {

        List<UserEntity> listUser = userRepository.findAll();
        if (listUser.isEmpty()) {
            return new WrapResponse<>(false, HttpStatusCodes.NO_CONTENT, "Danh sách user trống");
        }

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Lấy danh sách user thành công", listUser);
    }

    @Override
    public WrapResponse<?> saveAllUser(List<CreateUserRequest> requests) {
        if (requests.isEmpty()) {
            return new WrapResponse<>(false, HttpStatusCodes.NO_CONTENT, "Danh sách user trống");
        }
        List<UserEntity> users = new ArrayList<>();

        long numOfUser = userRepository.quantityOfUser();

        for (CreateUserRequest request : requests) {
            numOfUser += 1;
            String userCode = String.format("U%03d", numOfUser);
            UserEntity user = UserEntity.builder()
                    .id(String.valueOf(UUID.randomUUID()))
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .userCode(userCode)
                    .role(request.getRole())
                    .build();
            users.add(user);
        }

        userRepository.saveAll(users);

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Thêm danh sách user thành công", users);
    }

    @Override
    public WrapResponse<?> findAllUsers(FilterRequest filterRequest) {
        Sort.Direction direction = filterRequest.getSortDirection().equalsIgnoreCase("desc") ?
                Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                filterRequest.getPage(),
                filterRequest.getSize(),
                direction,
                filterRequest.getSortBy()
        );

        String[] filter = filterRequest.getFilter();
        Integer role = extractRoleFromFilter(filter);
        Boolean deleted = extractDeletedFromFilter(filter);

        Page<UserEntity> usersPage = userRepository.findAllFiltered(role, deleted, filterRequest.getKeyWord(), pageable);

        if (filterRequest.getPage() < 0 || filterRequest.getPage() > usersPage.getTotalPages()){
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Số trang không hợp lệ");
        }

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Danh sách user trang " + filterRequest.getPage() + ": ", usersPage);
    }

    private Integer extractRoleFromFilter(String[] filter) {
        if (filter != null) {
            for (String condition : filter) {
                String[] parts = condition.split(":");
                if (parts.length == 2 && parts[0].trim().equalsIgnoreCase("role")) {
                    return Integer.parseInt(parts[1].trim());
                }
            }
        }
        return null;
    }

    private Boolean extractDeletedFromFilter(String[] filter) {
        if (filter != null) {
            for (String condition : filter) {
                String[] parts = condition.split(":");
                if (parts.length == 2 && parts[0].trim().equalsIgnoreCase("deleted")) {
                    return Boolean.parseBoolean(parts[1].trim());
                }
            }
        }
        return null;
    }
}
