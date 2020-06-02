package com.invoices.client.api.requests;

import com.invoices.client.domain.Address;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateClientRequest {
    Long clientId;
    String name;
    String surname;
    String phoneNumber;
    Address address;
    Address deliveryAddress;
}
