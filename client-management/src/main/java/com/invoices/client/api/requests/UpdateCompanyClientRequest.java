package com.invoices.client.api.requests;

import com.invoices.client.domain.Address;
import com.invoices.client.domain.CompanyContacts;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UpdateCompanyClientRequest {
    Long clientId;
    String name;
    String nip;
    String phoneNumber;
    Address address;
    List<Address> deliveryAddress;
    List<CompanyContacts> contactPeople;
}
