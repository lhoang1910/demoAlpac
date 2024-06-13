package com.demo.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRelatives {
    private String name;
    private String old;
    private String jobTitle;

    @Override
    public String toString() {
        return "CustomerRelatives{" +
                "name='" + name + '\'' +
                ", old='" + old + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                '}';
    }
}
