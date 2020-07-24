package com.invoices.client.requests;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegisterClientRequest {
    private final String companyName;
    private final String nip;
    private final String vat;
    private final String country;
    private final String name;
    private final String surname;
    private final String dateOfBirth;
    private final Address address;
    private final String phoneNumber;
    private final String fax;
    private final List<Address> deliveryAddresses;

    @Data
    @Builder
    public static class Address {
        private final String country;
        private final String street;
        private final String houseNumber;
        private final String city;
        private final String zipCode;
    }
}
