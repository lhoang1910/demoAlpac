package com.demo.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasePageSortAndSearch {
    int page = 0;
    int size = 10;
    String sortBy = "userCode";
    String sortDirection = "asc";
    String keyWord;
}
