package com.demo.demo.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerIdentificationModel {

    @NotNull()
    @Range(min = 1, max = 5)
    private int identificationType;

    @NotNull
    @NotBlank
    private String idenId;
}
