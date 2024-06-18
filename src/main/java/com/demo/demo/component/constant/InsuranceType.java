package com.demo.demo.component.constant;

public enum  InsuranceType {

        BAO_HIEM_NHAN_THO("VNI", "Bảo hiểm Nhân thọ", 100.0, 50.0),
        BAO_HIEM_O_TO("VCI", "Bảo hiểm Ô tô", 200.0, 100.0),
        BAO_HIEM_Y_TE("VHI", "Bảo hiểm ý tế", 300.0, 150.0);

        private final String typeInsuranceCode;
        private final String nameInsuranceType;
        private final double paymentFee;
        private final double insuranceFee;

        InsuranceType(String typeInsuranceCode, String nameInsuranceType, double paymentFee, double insuranceFee) {
                this.typeInsuranceCode = typeInsuranceCode;
                this.nameInsuranceType = nameInsuranceType;
                this.paymentFee = paymentFee;
                this.insuranceFee = insuranceFee;
        }

        public String getTypeInsuranceCode() {
                return typeInsuranceCode;
        }

        public String getNameInsuranceType() {
                return nameInsuranceType;
        }

        public double getPaymentFee() {
                return paymentFee;
        }

        public double getInsuranceFee() {
                return insuranceFee;
        }

}
