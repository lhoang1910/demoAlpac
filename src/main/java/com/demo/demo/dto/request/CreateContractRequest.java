package com.demo.demo.dto.request;

import com.demo.demo.component.JsonProcess;
import com.demo.demo.component.constant.ContractStatus;
import com.demo.demo.component.constant.DateTimeFormat;
import com.demo.demo.entity.model.InsuranceModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContractRequest {

    @NotNull(message = "Start time cannot be null")
    @JsonFormat(pattern = DateTimeFormat.DATE_TIME_FORMAT)
    private Date startTime;

    @NotNull(message = "Expiration time cannot be null")
    @JsonFormat(pattern = DateTimeFormat.DATE_TIME_FORMAT)
    private Date expirationTime;

    private String customerCode;

    @Range(min = 0)
    private double totalAmountPaid;

    private int contractStatus;
    private int paymentStatus;

    private List<String> typeInsuranceCodes;

    public double calculateTotalInsuranceFee (){
        double totalInsuranceFee = 0;
        for (InsuranceModel i : findInsuranceTypeByCode()){
            totalInsuranceFee += i.getInsuranceFee();
        }
        return totalInsuranceFee;
    }

    public double calculateTotalPaymentAmount (){
        double totalPaymentAmount = 0;
        for (InsuranceModel i : findInsuranceTypeByCode()){
            totalPaymentAmount += i.getPaymentFee();
        }
        return totalPaymentAmount;
    }

    public List<InsuranceModel> findInsuranceTypeByCode(){

        List<InsuranceModel> insuranceList = new ArrayList<>();

        try {
            for (InsuranceModel insurance : JsonProcess.readJsonFile("C:/Users/hoang/Downloads/demoGradle/demo/src/main/resources/InsuranceType.json")) {
                if (this.typeInsuranceCodes.contains(insurance.getTypeInsuranceCode())) {
                    insuranceList.add(insurance);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return insuranceList;
    }

    public boolean isvalidityOfContractValid(){
        return this.getStartTime().before(this.getExpirationTime());
    }

    public int setContractPaymentStatus(){
        if (totalAmountPaid == 0){
            return 2;
        } else if (totalAmountPaid == this.calculateTotalPaymentAmount()){
            return 1;
        } else {
            return 3;
        }
    }

    public static Date getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeFormat.DATE_TIME_FORMAT);
        String currentTime = dateFormat.format(new Date());

        try {
            return dateFormat.parse(currentTime);
        } catch (Exception e) {
            return null;
        }
    }

    public int setContractStatus(){
        if (this.getExpirationTime().compareTo(getCurrentDate()) < 0){
            return ContractStatus.HET_HIEU_LUC;
        } else if (this.getStartTime().compareTo(getCurrentDate()) > 0) {
            return ContractStatus.CHUA_CO_HIEU_LUC;
        } else {
            return ContractStatus.DANG_CO_HIEU_LUC;
        }
    }

}
