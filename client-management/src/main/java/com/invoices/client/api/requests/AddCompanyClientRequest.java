package com.invoices.client.api.requests;

import com.invoices.client.domain.Address;
import com.invoices.client.domain.CompanyContacts;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AddCompanyClientRequest {
    String name;
    String nip;
    String phoneNumber;
    Address address;
    List<CompanyContacts> contactPeople;
    List<Address> deliveryAddresses;
}
