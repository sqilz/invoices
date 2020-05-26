package com.invoices.client.api.requests;

import com.invoices.client.domain.Address;
import com.invoices.client.domain.Person;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AddClientRequest {
    String companyName;
    List<Person> contactPersons;
    List<Address> deliveryAddresses;
}
