package com.invoices.client.api.requests;

import com.invoices.client.domain.Address;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Date;
import java.util.Set;

@Value
@Builder
public class UpdateClientRequest {
    Long id;
    String companyName;
    String nip;
    String vat;
    String country;

    String name;
    String surname;
    Date dateOfBirth;

    @NonNull
    Address address;
    @NonNull
    String phoneNumber;
    String fax;

    Set<Address> deliveryAddresses;
}
