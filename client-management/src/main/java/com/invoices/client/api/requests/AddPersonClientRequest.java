package com.invoices.client.api.requests;

import com.invoices.client.domain.Address;
import com.invoices.client.domain.DeliveryAddresses;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AddPersonClientRequest {
    String name;
    String surname;
    String phoneNumber;
    Address address;
    DeliveryAddresses deliveryAddresses;
}
