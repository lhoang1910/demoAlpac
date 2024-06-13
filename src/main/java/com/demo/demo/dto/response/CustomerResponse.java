package com.demo.demo.dto.response;

import com.demo.demo.entity.CustomerEntity;
import com.demo.demo.entity.json.CustomerAddressConverter;
import com.demo.demo.entity.json.CustomerIndentificationConverter;
import com.demo.demo.entity.json.CustomerRelativesConverter;
import com.demo.demo.entity.model.CustomerAddressModel;
import com.demo.demo.entity.model.CustomerIdentificationModel;
import com.demo.demo.entity.model.CustomerRelativeModel;
import jakarta.persistence.Convert;
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

    @Convert(converter = CustomerIndentificationConverter.class)
    private CustomerIdentificationModel identification;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;

    @Convert(converter = CustomerAddressConverter.class)
    private CustomerAddressModel address;
    private String jobTitle;

    @Convert(converter = CustomerRelativesConverter.class)
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
}
