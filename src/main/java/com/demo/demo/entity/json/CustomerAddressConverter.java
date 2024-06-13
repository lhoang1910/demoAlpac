package com.demo.demo.entity.json;

import com.demo.demo.entity.model.CustomerAddressModel;
import jakarta.persistence.AttributeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerAddressConverter implements AttributeConverter<CustomerAddressModel, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CustomerAddressModel address) {
        if (address == null){
            return null;
        }
        try {
            return objectMapper.writeValueAsString(address);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerAddressModel convertToEntityAttribute(String dbData) {
        if (dbData == null){
            return null;
        }
        try {
            return objectMapper.readValue(dbData, CustomerAddressModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

