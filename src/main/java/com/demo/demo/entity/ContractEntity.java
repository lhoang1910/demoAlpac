package com.demo.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "contracts")
public class ContractEntity {

    @Id
    private String id;

    @Column(name = "contract_code")
    private String contractCode;
    private long customerCode;
    private Long totalPayment ;
    private Long totalInsurance	;
    private Long totalAmountToBePaid;
    private Long totalAmountPaid;
//    private PaymentStatus paymentStatus;
//    private ContractStatus contractStatus;
}
