package com.demo.demo.dto.response;

import com.demo.demo.component.constant.IdentificationType;
import com.demo.demo.component.constant.DateTimeFormat;
import com.demo.demo.entity.CustomerEntity;
import com.demo.demo.entity.model.CustomerAddressModel;
import com.demo.demo.entity.model.CustomerIdentificationModel;
import com.demo.demo.entity.model.CustomerRelativeModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private String customerCode;
    private String name;
    private String gender;

    private CustomerIdentificationModel identification;
    private String phoneNumber;
    private String email;

    @JsonFormat(pattern = DateTimeFormat.DATE_FORMAT)
    private Date dateOfBirth;

    private CustomerAddressModel address;
    private String jobTitle;

    private List<CustomerRelativeModel> customerRelatives;
    private int status;

    public CustomerResponse(CustomerEntity newCustomer) {
        this.customerCode = newCustomer.getCustomerCode();
        this.name = newCustomer.getName();
        this.gender = newCustomer.getGender();
        this.identification = newCustomer.getIdentification();
        this.phoneNumber = newCustomer.getPhoneNumber();
        this.email = newCustomer.getEmail();
        this.dateOfBirth = newCustomer.getDateOfBirth();
        this.address = newCustomer.getAddress();
        this.jobTitle = newCustomer.getJobTitle();
        this.customerRelatives = newCustomer.getCustomerRelatives();
        this.status = newCustomer.getStatus();
    }

    public String setIdenTypeName(int role){

        return switch (role) {
            case IdentificationType.CCCD -> "Căn cước công dân";
            case IdentificationType.GIAY_KHAI_SINH -> "Giấy khai sinh";
            case IdentificationType.HO_CHIEU -> "Hộ Chiếu";
            case IdentificationType.CMND -> "Chứng minh nhân dân";
            default -> "kHÔNG XÁC ĐỊNH";
        };
    }

}
