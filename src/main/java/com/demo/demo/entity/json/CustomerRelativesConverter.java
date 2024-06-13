package com.demo.demo.entity.json;

import com.demo.demo.entity.model.CustomerRelativeModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.springframework.stereotype.Component;

@Component
public class CustomerRelativesConverter implements AttributeConverter<CustomerRelativeModel, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(CustomerRelativeModel relative) {
        if (relative == null){
            return null;
        }
        try {
            return objectMapper.writeValueAsString(relative);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CustomerRelativeModel convertToEntityAttribute(String dbData) {
        if (dbData == null){
            return null;
        }
        try {
            return objectMapper.readValue(dbData, CustomerRelativeModel.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
