package com.demo.demo.entity.json;

import com.demo.demo.entity.model.CustomerIdentificationModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

@Component
public class CustomerIndentificationConverter implements AttributeConverter<CustomerIdentificationModel, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CustomerIdentificationModel identification) {
        if (identification == null){
            return null;
        }
        try {
            return objectMapper.writeValueAsString(identification);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerIdentificationModel convertToEntityAttribute(String dbData) {
        if (dbData == null){
            return null;
        }
        try {
            return objectMapper.readValue(dbData, CustomerIdentificationModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

