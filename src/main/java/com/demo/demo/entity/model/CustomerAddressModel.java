package com.demo.demo.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAddressModel {
    private String number;
    private String street;
    private String district;
    private String city;

    @NotBlank
    private String country;

    public boolean validate() {
        if (this.number == null || this.number.isEmpty()) {
            return false;
        }
        if (this.street == null || this.street.isEmpty()) {
            return false;
        }
        if (this.district == null || this.district.isEmpty()) {
            return false;
        }
        if (this.city == null || this.city.isEmpty()) {
            return false;
        }
        return this.country != null && !this.country.isEmpty();
    }

}
