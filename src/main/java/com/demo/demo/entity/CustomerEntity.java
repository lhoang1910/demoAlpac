package com.demo.demo.entity;

import com.demo.demo.entity.json.CustomerAddressConverter;
import com.demo.demo.entity.json.CustomerIndentificationConverter;
import com.demo.demo.entity.json.CustomerRelativesConverter;
import com.demo.demo.entity.model.CustomerAddressModel;
import com.demo.demo.entity.model.CustomerIdentificationModel;
import com.demo.demo.entity.model.CustomerRelativeModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "customers")

public class CustomerEntity {
    @Id
    private String id;

    @Column(name = "customer_code")
    private String customerCode;
    private String name;
    private String gender;

    @Convert(converter = CustomerIndentificationConverter.class)
    @Column(columnDefinition = "json")
    private CustomerIdentificationModel identification;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;


    @Column(columnDefinition = "json")
    private String addressJson;

    @Convert(converter = CustomerAddressConverter.class)
    @Transient
    private CustomerAddressModel address;
    private String jobTitle;

    @Convert(converter = CustomerRelativesConverter.class)
    @Column(columnDefinition = "json")
    private List<CustomerRelativeModel> customerRelatives;

    public void setAddress(CustomerAddressModel customerAddressModel) {
        this.address = address;
        if (address != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                this.addressJson = objectMapper.writeValueAsString(customerAddressModel);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    private int status;

    private boolean isDelete;
}
