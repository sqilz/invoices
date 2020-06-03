package com.invoices.client.api.requests;

import com.invoices.client.domain.Address;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UpdatePersonClientRequest {
    Long clientId;
    String name;
    String surname;
    String phoneNumber;
    Address address;
    List<Address> deliveryAddress;
}
