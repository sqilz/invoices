package com.invoices.client.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AddressDto {
    long id;

    String country;
    String street;
    String houseNumber;
    String city;
    String zipCode;
}
