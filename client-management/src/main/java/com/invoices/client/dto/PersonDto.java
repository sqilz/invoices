package com.invoices.client.dto;

import com.invoices.client.domain.Address;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
class PersonDto {
    String name;
    String surname;
    Address address;
    String phoneNumber;
    List<Address> deliveryAddresses = new ArrayList<>();
}
