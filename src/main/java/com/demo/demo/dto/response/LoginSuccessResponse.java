package com.demo.demo.dto.response;

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
            case 1:
                roleName = "ADMIN";
                break;
            case 2:
                roleName = "TRƯỞNG PHÒNG";
                break;
            case 3:
                roleName = "PHÓ PHÒNG";
                break;
            case 4:
                roleName = "NHÂN VIÊN";
                break;
            default:
                roleName = "kHÔNG XÁC ĐỊNH";
        }
        return roleName;
    }

}
