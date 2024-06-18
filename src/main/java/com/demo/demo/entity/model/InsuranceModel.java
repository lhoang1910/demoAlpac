package com.demo.demo.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsuranceModel {

    private String typeInsuranceCode;   // Mã loại hình
    private String nameInsuranceType;   // Tên loại hình
    private double paymentFee;          // Số tiền phí thanh toán
    private double insuranceFee;        // Số tiền phí bảo hiểm

}
