package com.demo.demo.entity.model;

import com.demo.demo.component.constant.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidityOfTheContractModel {

    @NotNull(message = "Start time cannot be null")
    @JsonFormat(pattern = DateTimeFormat.DATE_TIME_FORMAT)
    private Date startTime;

    @NotNull(message = "Expiration time cannot be null")
    @JsonFormat(pattern = DateTimeFormat.DATE_TIME_FORMAT)
    private Date expirationTime;

}
