package com.invoices.client.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientQueryDto {

    Long id;
    String companyName;
    String nip;
    String vat;

    String name;
    String surname;
    String dateOfBirth;

    AddressDto address;
    String phoneNumber;
    String fax;
}
