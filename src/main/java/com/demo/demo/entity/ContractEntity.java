package com.demo.demo.entity;

import com.demo.demo.component.constant.DateTimeFormat;
import com.demo.demo.component.constant.Jpa_Type;
import com.demo.demo.entity.model.InsuranceModel;
import com.demo.demo.entity.model.ValidityOfTheContractModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "contracts")
@TypeDef(name = Jpa_Type.JSON_BINARY, typeClass = JsonBinaryType.class)
@Builder
public class ContractEntity {

    @Id
    private String id;

    @Column(name = "contract_code")
    private String contractCode;

    @JsonFormat(pattern = DateTimeFormat.DATE_TIME_FORMAT)
    private Date startTime;

    @JsonFormat(pattern = DateTimeFormat.DATE_TIME_FORMAT)
    private Date expirationTime;

    private String customerCode;

    private double totalPaymentAmount ;   // Tổng phí thanh toán
    private double totalInsuranceAmount;  // Tổng phí bảo hiểm

    private double totalAmountToBePaid; // Số tiền cần thanh toán
    private double totalAmountPaid; // Số tiền đã thanh toán

    @Column(nullable = true)
    private int contractStatus;

    @Column(nullable = true)
    private int paymentStatus;

    @Type(type = Jpa_Type.JSON_BINARY)
    @Column(columnDefinition = Jpa_Type.JSON_BINARY)
    private List<InsuranceModel> insurances;    // Danh sách loại hình bảo hiểm đã tham gia

    @Column(nullable = true)
    private boolean isDelete;

}
