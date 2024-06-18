package com.demo.demo.service.Impl;

import com.demo.demo.component.constant.CustomerStatus;
import com.demo.demo.component.constant.HttpStatusCodes;
import com.demo.demo.component.constant.IdentificationType;
import com.demo.demo.dto.request.CreateCustomerRequest;
import com.demo.demo.dto.response.CustomerResponse;
import com.demo.demo.dto.response.WrapResponse;
import com.demo.demo.entity.CustomerEntity;
import com.demo.demo.entity.model.CustomerAddressModel;
import com.demo.demo.entity.model.CustomerIdentificationModel;
import com.demo.demo.entity.model.CustomerRelativeModel;
import com.demo.demo.repository.CustomerRepository;
import com.demo.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public static boolean validIndentification(CustomerIdentificationModel identification) {
        String regex = "";
        switch (identification.getIdentificationType()) {
            case IdentificationType.CCCD:
                regex = "^\\d{12}$";
                break;
            case IdentificationType.GIAY_KHAI_SINH:
                regex = "^\\d{12}$";
                break;
            case IdentificationType.HO_CHIEU:
                regex = "^[A-Z]\\d{7}$";
                break;
            case IdentificationType.CMND:
                regex = "^\\d{9}$";
                break;
            default:
                return false;
        }

        Pattern identificationPattern = Pattern.compile(regex);
        Matcher matcher = identificationPattern.matcher(identification.getIdenId());
        return !matcher.matches();
    }

    @Override
    public WrapResponse<?> createCustomer(CreateCustomerRequest request) {

        long numOfCustomer = customerRepository.quantityOfCustomer() + 1;
        String customerCode = String.format("C%03d", numOfCustomer);

        if (validIndentification(request.getIdentificationModel())) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Giấy tờ tùy thân không đúng định dạng");
        }

        if (request.isDateOfBirthValid()) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Ngày, tháng, năm, sinh không đúng định dạng");
        }

        String country = request.getCountry();
        CustomerAddressModel address = null;
        if (!"việtnam".equalsIgnoreCase(country)) {
            if (request.isAddressValid()) {
                address = new CustomerAddressModel(request.getNumber(), request.getStreet(),
                        request.getDistrict(), request.getCity(), request.getCountry());
            } else {
                return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Địa chỉ thuộc quốc gia Việt Nam thì phải nhập đầy đủ thông tin");
            }
        }

        if (request.getCustomerRelatives() == null || request.getCustomerRelatives().isEmpty()) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Phải điền tối thiểu một thân nhân");
        }
        List<CustomerRelativeModel> relatives = new ArrayList<>(request.getCustomerRelatives());

        CustomerEntity newCustomer = CustomerEntity.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .customerCode(customerCode)
                .isDelete(false)
                .status(CustomerStatus.TIEM_NANG)
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .gender(request.getGender())
                .jobTitle(request.getJobTitle())
                .name(request.getName())
                .identification(request.getIdentificationModel())
                .dateOfBirth(request.getDateOfBirth())
                .customerRelatives(relatives)
                .address(address).build();


        customerRepository.save(newCustomer);

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Thêm khách hàng thành công", new CustomerResponse(newCustomer));
    }

    @Override
    public WrapResponse<?> findAllBeNotDeleteCustomer() {
        List<CustomerEntity> customerEntities = customerRepository.findAllByIsDelete(false);

        if (customerEntities.isEmpty()) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Danh sách khác hàng chưa bị xóa rỗng");
        }

        List<CustomerResponse> responses = new ArrayList<>();

        for (CustomerEntity c : customerEntities) {
            responses.add(new CustomerResponse(c));
        }

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Lấy danh sách khách hàng chưa bị xóa thành công", responses);
    }

    @Override
    public WrapResponse<?> deleteCustomerById(String id) {
        Optional<CustomerEntity> existCustomer = customerRepository.findByIsDeleteAndId(false, id);
        if (existCustomer.isEmpty()) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Không tồn tại khách hàng với Id: " + id);
        }

        existCustomer.get().setDelete(true);
        customerRepository.save(existCustomer.get());
        return new WrapResponse<>(true, HttpStatusCodes.OK, "Xóa khách hàng " + existCustomer.get().getCustomerCode() + " thành công");
    }

    @Override
    public WrapResponse<?> updateCustomerById(String id, CreateCustomerRequest request) {

        CustomerEntity existCustomer = customerRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Không tồn tại Customer với Id: " + id));

        if (validIndentification(request.getIdentificationModel())) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Giấy tờ tùy thân không đúng định dạng");
        }

        if (request.isDateOfBirthValid()) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Ngày, tháng, năm, sinh không đúng định dạng");
        }

        String country = request.getCountry();
        CustomerAddressModel address = null;
        if (!"việtnam".equalsIgnoreCase(country)) {
            if (request.isAddressValid()) {
                address = new CustomerAddressModel(request.getNumber(), request.getStreet(),
                        request.getDistrict(), request.getCity(), request.getCountry());
            } else {
                return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Địa chỉ thuộc quốc gia Việt Nam thì phải nhập đầy đủ thông tin");
            }
        }

        if (request.getCustomerRelatives() == null || request.getCustomerRelatives().isEmpty()) {
            return new WrapResponse<>(false, HttpStatusCodes.BAD_REQUEST, "Phải điền tối thiểu một thân nhân");
        }
        List<CustomerRelativeModel> relatives = new ArrayList<>(request.getCustomerRelatives());

        existCustomer = CustomerEntity.builder()
                .id(existCustomer.getId())
                .customerCode(existCustomer.getCustomerCode())
                .status(request.getStatus())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .gender(request.getGender())
                .jobTitle(request.getJobTitle())
                .name(request.getName())
                .identification(request.getIdentificationModel())
                .dateOfBirth(request.getDateOfBirth())
                .customerRelatives(relatives)
                .address(address)
                .build();

        customerRepository.save(existCustomer);

        return new WrapResponse<>(true, HttpStatusCodes.OK, "Chỉnh sửa thông tin khách hàng thành công", new CustomerResponse(existCustomer));
    }


}
