package com.invoices.client.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private String street;
    private String houseNumber;
    private String city;
    private String zipCode;
}
