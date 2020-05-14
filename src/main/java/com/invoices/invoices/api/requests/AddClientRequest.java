package com.invoices.invoices.api.requests;

import lombok.Value;

import java.util.List;

@Value
public class AddClientRequest {
    String companyName;
    List<ContactPersons> contactPersons;
    List<DeliveryAddresses> deliveryAddresses;


    public static class ContactPersons {

    }

    public static class DeliveryAddresses {

    }
}
