package com.demo.demo.entity;

import com.demo.demo.component.constant.Jpa_Type;
import com.demo.demo.component.constant.DateTimeFormat;
import com.demo.demo.entity.model.CustomerAddressModel;
import com.demo.demo.entity.model.CustomerIdentificationModel;
import com.demo.demo.entity.model.CustomerRelativeModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "customers")
@TypeDef(name = Jpa_Type.JSON_BINARY, typeClass = JsonBinaryType.class)
@Builder
public class CustomerEntity {
    @Id
    private String id;

    @Column(name = "customer_code")
    private String customerCode;
    private String name;
    private String gender;

    @Type(type = Jpa_Type.JSON_BINARY)
    @Column(columnDefinition = Jpa_Type.JSON_BINARY)
    private CustomerIdentificationModel identification;
    private String phoneNumber;
    private String email;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = DateTimeFormat.DATE_FORMAT)
    private Date dateOfBirth;

    @Type(type = Jpa_Type.JSON_BINARY)
    @Column(columnDefinition = Jpa_Type.JSON_BINARY)
    private CustomerAddressModel address;
    private String jobTitle;

    @Type(type = Jpa_Type.JSON_BINARY)
    @Column(columnDefinition = Jpa_Type.JSON_BINARY)
    private List<CustomerRelativeModel> customerRelatives;

    @Column(nullable = true)
    private int status;

    @Column(nullable = true)
    private boolean isDelete;

}
