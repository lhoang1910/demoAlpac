package com.demo.demo.dto.response;

import com.demo.demo.component.constant.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessResponse {

    private String userCode;
    private String username;
    private String role;
    private boolean deleted;

    public String setRoleName(int role){
        String roleName = "";
        switch (role){
            case UserRole.ROLE_ADMIN:
                roleName = "ADMIN";
                break;
            case UserRole.ROLE_TRUONG_PHONG:
                roleName = "TRƯỞNG PHÒNG";
                break;
            case UserRole.ROLE_PHO_PHONG:
                roleName = "PHÓ PHÒNG";
                break;
            case UserRole.ROLE_NHAN_VIEN:
                roleName = "NHÂN VIÊN";
                break;
            default:
                roleName = "kHÔNG XÁC ĐỊNH";
        }
        return roleName;
    }

}
