package com.demo.demo.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotNull(message = "không được để trống username")
    @Size(min = 6, max = 15, message = "Username phải có từ 6 đến 15 ký tự")
    @NotEmpty(message = "Không được để trống username")
    @NotBlank(message = "Không được để trống username")
    @Pattern(regexp = "\\S+", message = "Username không được chứa khoảng trắng")
    private String username;

    @NotNull(message = "không được để trống password")
    @Size(min = 8, max = 15, message = "Password phải có từ 8 đến 15 ký tự")
    @NotEmpty(message = "Không được để trống password")
    @NotBlank(message = "Không được để trống password")
    @Pattern(regexp = "\\S+", message = "Password không được chứa khoảng trắng")
    private String password;

    private int role;
}
