package com.invoices.client.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Person extends Client {
    private String surname;

    @Builder
    public Person(String name, Address address, String phoneNumber, List<Address> deliveryAddresses, String surname) {
        super(name, address, phoneNumber, deliveryAddresses);
        this.surname = surname;
    }
}
