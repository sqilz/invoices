package com.invoices.client.api.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoices.client.domain.Address;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;

@Value
public class RegisterClientRequest {
    String companyName;
    String nip;
    String vat;

    String name;
    String surname;

    String dateOfBirth;

    @NonNull
    Address address;
    @NonNull
    String phoneNumber;
    String fax;

    Set<Address> deliveryAddresses;

    @JsonCreator
    @Builder
    public RegisterClientRequest(@JsonProperty("companyName") String companyName,
                                 @JsonProperty("nip") String nip,
                                 @JsonProperty("vat") String vat,
                                 @JsonProperty("name") String name,
                                 @JsonProperty("surname") String surname,
                                 @JsonProperty("dateOfBirth") String dateOfBirth,
                                 @JsonProperty("address") Address address,
                                 @JsonProperty("phoneNumber") String phoneNumber,
                                 @JsonProperty("fax") String fax,
                                 @JsonProperty("deliveryAddresses") Set<Address> deliveryAddresses) {
        this.companyName = companyName;
        this.nip = nip;
        this.vat = vat;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
        this.deliveryAddresses = deliveryAddresses;
    }
}
