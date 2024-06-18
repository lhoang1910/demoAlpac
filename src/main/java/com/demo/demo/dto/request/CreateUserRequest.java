package com.demo.demo.dto.request;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @Size(min = 6, max = 15, message = "Username phải có từ 6 đến 15 ký tự")
    @NotBlank(message = "Không được để trống username")
    @Pattern(regexp = "\\S+", message = "Username không được chứa khoảng trắng")
    private String username;

    @Size(min = 8, max = 15, message = "Password phải có từ 8 đến 15 ký tự")
    @NotBlank(message = "Không được để trống password")
    @Pattern(regexp = "\\S+", message = "Password không được chứa khoảng trắng")
    private String password;

    private int role;
}
