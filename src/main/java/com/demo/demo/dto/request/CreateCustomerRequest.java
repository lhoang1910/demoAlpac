package com.demo.demo.dto.request;
import com.demo.demo.entity.model.CustomerRelativeModel;
import jakarta.validation.constraints.*;
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

    private final String DATE_FORMAT = "yyyy:MM:dd";

    @NotNull(message = "không được để trống tên")
    @NotEmpty(message = "Không được để trống tên")
    @NotBlank(message = "Không được để trống tên")
    private String name;

    @NotNull(message = "không được để trống giới tính")
    @NotEmpty(message = "Không được để trống giới tính")
    @NotBlank(message = "Không được để trống giới tính")
    private String gender;

    @NotNull(message = "không được để trống kiểu định danh")
    private int identificationType;

    private String idenId;

    @Pattern(regexp = "^[\\d\\+]{9,15}$")
    private String phoneNumber;

    @Pattern(regexp = "^[\\w-_.]+@[\\w]+\\.[\\w]+$")
    private String email;

    private Date dateOfBirth;

    private String number;
    private String street;
    private String district;
    private String city;

    @NotNull(message = "không được để trống quốc gia")
    @NotEmpty(message = "Không được để trống quốc gia")
    @NotBlank(message = "Không được để trống quốc gia")
    private String country;

    @NotNull(message = "không được để trống nghề nghiệp")
    @NotEmpty(message = "Không được để trống nghề nghiệp")
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
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(sdf.format(dateOfBirth));
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


}
