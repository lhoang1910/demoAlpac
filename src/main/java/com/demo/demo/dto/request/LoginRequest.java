package com.demo.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "Không được để trống username")
    @NotEmpty(message = "Không được để trống username")
    @NotBlank(message = "Không được để trống username")
    private String username;

    @NotNull(message = "Không được để trống password")
    @NotEmpty(message = "Không được để trống password")
    @NotBlank(message = "Không được để trống password")
    private String password;
}
