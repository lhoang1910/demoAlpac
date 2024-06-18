package com.demo.demo.service.Impl;

import com.demo.demo.component.constant.ContractStatus;
import com.demo.demo.component.constant.HttpStatusCodes;
import com.demo.demo.dto.ContractStatusCheck;
import com.demo.demo.dto.request.CreateContractRequest;
import com.demo.demo.dto.response.WrapResponse;
import com.demo.demo.entity.ContractEntity;
import com.demo.demo.entity.CustomerEntity;
import com.demo.demo.repository.ContractRepository;
import com.demo.demo.repository.CustomerRepository;
import com.demo.demo.schedule.SheduleContractStatusTracking;
import com.demo.demo.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ContractRepository contractRepository;

    private static final Logger log = LoggerFactory.getLogger(SheduleContractStatusTracking.class);

    @Override
    public WrapResponse<?> createContract(CreateContractRequest request){

        if (request.getTotalAmountPaid() > request.calculateTotalPaymentAmount()) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Số tiền đã thanh toán phải nhỏ hơn tổng số tiền phải thanh toán");
        }

        if (!request.isvalidityOfContractValid()){
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Thời gian hết hạn phải ở sau thời gian khởi tạo hợp đồng");
        }

        Optional<CustomerEntity> customer = customerRepository.findByCustomerCode(request.getCustomerCode());

        if (customer.isEmpty()) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Không tồn tại khách hàng với mã khách hàng: " + request.getCustomerCode());
        }

        long numOfContract = contractRepository.quantityOfContract() + 1;
        String contractCode = String.format("CT%03d", numOfContract);



        ContractEntity contract = ContractEntity.builder()
                .contractCode(contractCode)
                .id(String.valueOf(UUID.randomUUID()))
                .startTime(request.getStartTime())
                .expirationTime(request.getExpirationTime())
                .totalPaymentAmount(request.calculateTotalPaymentAmount())
                .totalInsuranceAmount(request.calculateTotalInsuranceFee())
                .totalAmountToBePaid(request.calculateTotalPaymentAmount() - request.getTotalAmountPaid())
                .totalAmountPaid(request.getTotalAmountPaid())
                .paymentStatus(request.setContractPaymentStatus())
                .contractStatus(request.setContractStatus())
                .insurances(request.findInsuranceTypeByCode())
                .customerCode(request.getCustomerCode())
                .isDelete(false)
                .build();

        contractRepository.save(contract);

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Thêm hợp đồng thành công", contract);
    }

    @Override
    public WrapResponse<?> updateContract(String id, CreateContractRequest request) throws IOException {
        Optional<ContractEntity> existContract = contractRepository.findById(id);

        if (existContract.isEmpty()){
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Hợp đồng không tồn tại");
        }
        if (!request.isvalidityOfContractValid()){
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Thời gian hết hạn phải ở sau thời gian khởi tạo hợp đồng");
        }

        Optional<CustomerEntity> customer = customerRepository.findByCustomerCode(request.getCustomerCode());
        if (customer.isEmpty()) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Không tồn tại khách hàng với mã khách hàng: " + request.getCustomerCode());
        }

        ContractEntity contract = existContract.get();
        contract.setCustomerCode(request.getCustomerCode());
        contract.setTotalAmountPaid(request.getTotalAmountPaid());
        contract.setTotalAmountToBePaid(request.calculateTotalPaymentAmount() - request.getTotalAmountPaid());
        contract.setPaymentStatus(request.setContractPaymentStatus());
        contract.setStartTime(request.getStartTime());
        contract.setExpirationTime(request.getExpirationTime());

        contractRepository.save(contract);

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Cập nhật hợp đồng thành công", contract);

    }

    @Override
    public WrapResponse<?> deleteContractByContractId(String id) {

        Optional<ContractEntity> existContract = contractRepository.findByIdAndIsDelete(id, false);

        if (existContract.isEmpty()){
            return new WrapResponse<>(false, HttpStatusCodes.NOT_FOUND, "Không tồn tại hợp đồng");
        }

        existContract.get().setDelete(true);
        contractRepository.save(existContract.get());

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Xóa hợp đồng thành công");
    }

    @Override
    public WrapResponse<?> cancelContract(String id) {

        Optional<ContractEntity> existContract = contractRepository.findById(id);

        if (existContract.isEmpty()){
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Hợp đồng không tồn tại");
        }

        ContractEntity contract = existContract.get();
        contract.setContractStatus(ContractStatus.DA_CHAM_DUT);
        contractRepository.save(contract);

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Chấm dứt đồng thành công");
    }

    @Override
    public void trackingContractStatus() {
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(currentTimeMillis);

        List<ContractStatusCheck> contractCanBeExpire = contractRepository.findAllContractsCanBeExpire();
        List<ContractStatusCheck> contractCanBeValid = contractRepository.findAllContractsCanBeValid();
        System.out.println(contractCanBeExpire);
        System.out.println(contractCanBeValid);

        updateContractStatus(contractCanBeExpire, ContractStatus.HET_HIEU_LUC);
        updateContractStatus(contractCanBeValid, ContractStatus.DANG_CO_HIEU_LUC);

    }

    private void updateContractStatus(List<ContractStatusCheck> contracts, int newStatus) {
        List<ContractEntity> updatedContracts = new ArrayList<>();

        for (ContractStatusCheck statusCheck : contracts) {
            ContractEntity contract = (ContractEntity) contractRepository.findByContractCode(statusCheck.getContractCode()).orElse(null);
            if (contract != null && contract.getContractStatus() != newStatus) {
                contract.setContractStatus(newStatus);
                log.info("Hợp đồng " + contract.getContractCode() + " đã được cập nhật trạng thái thành " + newStatus);
                updatedContracts.add(contract);
            }
        }

        if (!updatedContracts.isEmpty()) {
            contractRepository.saveAll(updatedContracts);
        }
    }

}
