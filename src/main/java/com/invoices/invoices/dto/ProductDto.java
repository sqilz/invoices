package com.invoices.invoices.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String code;
    private String description;
    private double price;
    private int discount;

    private List<String> attributes;
}
