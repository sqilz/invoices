package com.invoices.client.api.requests;

import com.invoices.client.domain.Address;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class AddPersonClientRequest {
    String name;
    String surname;
    String phoneNumber;
    Address address;
    Set<Address> deliveryAddresses;
}
