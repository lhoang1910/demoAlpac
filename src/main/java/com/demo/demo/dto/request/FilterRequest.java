package com.demo.demo.dto.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FilterRequest extends BasePageSortAndSearch{
    String[] filter;
}
