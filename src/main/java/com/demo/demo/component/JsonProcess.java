package com.demo.demo.component;

import com.demo.demo.entity.model.InsuranceModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonProcess {

    public static List<InsuranceModel> readJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            return objectMapper.readValue(file, new TypeReference<List<InsuranceModel>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void writeJsonFile(List<InsuranceModel> insuranceTypes, String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(filePath), insuranceTypes);
    }

}
