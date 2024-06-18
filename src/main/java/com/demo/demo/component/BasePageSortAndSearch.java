package com.demo.demo.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasePageSortAndSearch {
    private int page = 0;
    private int size = 10;
    private String keyWord;
    private String sortDirection = "asc";
    private String sortBy = "userCode";
    private Integer role;
    private Boolean deleted;
}
