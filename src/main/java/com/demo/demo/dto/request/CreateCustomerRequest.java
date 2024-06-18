package com.demo.demo.dto.request;
import com.demo.demo.component.constant.DateTimeFormat;
import com.demo.demo.entity.model.CustomerIdentificationModel;
import com.demo.demo.entity.model.CustomerRelativeModel;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerRequest {

    @NotBlank(message = "Không được để trống tên")
    private String name;

    @NotBlank(message = "Không được để trống giới tính")
    private String gender;

    private CustomerIdentificationModel identificationModel;

    @Pattern(regexp = "^[\\d\\+]{9,15}$")
    private String phoneNumber;

    @Pattern(regexp = "^[\\w-_.]+@[\\w]+\\.[\\w]+$")
    private String email;

    private Date dateOfBirth;

    private String number;
    private String street;
    private String district;
    private String city;

    @NotBlank(message = "Không được để trống quốc gia")
    private String country;

    @NotBlank(message = "Không được để trống nghề nghiệp")
    private String jobTitle;

    private List<CustomerRelativeModel> customerRelatives;
    private int status;

    public boolean isAddressValid() {
        return !(isNullOrEmpty(number) || isNullOrEmpty(street) || isNullOrEmpty(district) || isNullOrEmpty(city));
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public boolean isDateOfBirthValid() {
        if (dateOfBirth == null) {
            return true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DateTimeFormat.DATE_FORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(sdf.format(dateOfBirth));
            return false;
        } catch (ParseException e) {
            return true;
        }
    }


}
