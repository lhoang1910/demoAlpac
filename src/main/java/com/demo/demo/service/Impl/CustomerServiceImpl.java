package com.demo.demo.service.Impl;

import com.demo.demo.constant.CustomerStatus;
import com.demo.demo.constant.IdentificationType;
import com.demo.demo.dto.request.CreateCustomerRequest;
import com.demo.demo.dto.response.CustomerResponse;
import com.demo.demo.entity.CustomerEntity;
import com.demo.demo.entity.json.CustomerAddressConverter;
import com.demo.demo.entity.json.CustomerIndentificationConverter;
import com.demo.demo.entity.json.CustomerRelativesConverter;
import com.demo.demo.entity.model.CustomerAddressModel;
import com.demo.demo.entity.model.CustomerIdentificationModel;
import com.demo.demo.entity.model.CustomerRelativeModel;
import com.demo.demo.repository.CustomerRepository;
import com.demo.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    CustomerIndentificationConverter idenConverter;
    CustomerAddressConverter addressConverter;
    CustomerRelativesConverter relativesConverter;

    @Autowired
    public CustomerServiceImpl(CustomerAddressConverter addressConverter, CustomerRelativesConverter relativesConverter, CustomerIndentificationConverter idenConverter) {
        this.addressConverter = addressConverter;
        this.relativesConverter = relativesConverter;
        this.idenConverter = idenConverter;
    }

    public static boolean validIndentification(int typeOfIndentification, String idenId){
        String regax = "";
        switch (typeOfIndentification){
            case IdentificationType.CCCD:
                regax = "^\\d{12}$\n";
                break;
            case IdentificationType.GIAY_KHAI_SINH:
                regax = "^\\d{12}$\n";
                break;
            case IdentificationType.HO_CHIEU:
                regax = "^[A-Z][0-9]{7}$\n";
                break;
            case IdentificationType.CMND:
                regax = "^\\d{9}$\n";
                break;
            default:
                return false;
        }

        Pattern indenPattern = Pattern.compile(regax);
        Matcher matcher = indenPattern.matcher(idenId);
        if (matcher.matches()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        CustomerEntity newCustomer = new CustomerEntity();
        newCustomer.setDelete(false);
        newCustomer.setId(String.valueOf(UUID.randomUUID()));
        newCustomer.setStatus(CustomerStatus.TIEM_NANG);
        newCustomer.setPhoneNumber(request.getPhoneNumber());
        newCustomer.setEmail(request.getEmail());
        newCustomer.setGender(request.getGender());
        newCustomer.setJobTitle(request.getJobTitle());
        newCustomer.setName(request.getName());

        long numOfCustomer = customerRepository.quantityOfCustomer() + 1;
        String customerCode = String.format("U%03d",numOfCustomer);
        newCustomer.setCustomerCode(customerCode);

        if (validIndentification(request.getIdentificationType(), request.getIdenId())){
            newCustomer.setIdentification(new CustomerIdentificationModel(request.getIdentificationType(), request.getIdenId()));
        }

        if (request.isDateOfBirthValid()){
            newCustomer.setDateOfBirth(request.getDateOfBirth());
        }

        if (!request.getCountry().trim().equalsIgnoreCase("việtnam")){
            if (request.isAddressValid()){
                newCustomer.setAddress(new CustomerAddressModel(request.getNumber(), request.getStreet(),
                        request.getDistrict(), request.getCity(), request.getCountry()));
            } else {
                return null;
            }
        }

        if (request.getCustomerRelatives() != null && !request.getCustomerRelatives().isEmpty()) {
            List<CustomerRelativeModel> relatives = new ArrayList<>();
            for (CustomerRelativeModel relative : request.getCustomerRelatives()) {
                relatives.add((CustomerRelativeModel) relative);
            }
            newCustomer.setCustomerRelatives(relatives);
            System.out.println(newCustomer);
            customerRepository.save(newCustomer);
        }

        return new CustomerResponse(newCustomer);
    }

    @Override
    public List<CustomerEntity> findAllVietNamCountryCustomer() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();

//        List<CustomerResponse> responses = new ArrayList<>();
//
//        customerEntities = customerEntities.stream().filter(s -> "Việt nam".equals(s.getAddress().getCountry()))
//                .toList();
//
//        for (CustomerEntity c : customerEntities){
//            responses.add(new CustomerResponse(c));
//        }

        return customerEntities;
    }
}
