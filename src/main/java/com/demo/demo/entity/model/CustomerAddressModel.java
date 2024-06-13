package com.demo.demo.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressModel {
    private String number;
    private String street;
    private String district;
    private String city;
    private String country;
}
