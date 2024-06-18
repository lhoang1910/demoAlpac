package com.demo.demo.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRelativeModel {

    @NotBlank
    private String name;

    @NotNull
    @Min(value = 0)
    private float old;

    @NotBlank
    private String jobTitle;
}
