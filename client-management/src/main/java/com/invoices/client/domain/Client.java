package com.invoices.client.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElse;

@Entity
@NoArgsConstructor
@Getter
public abstract class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    Address address;
    String phoneNumber;

    @OneToMany
    private List<Address> deliveryAddresses;

    @Builder
    Client(String name, Address address, String phoneNumber, List<Address> deliveryAddresses) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.deliveryAddresses = deliveryAddresses;
    }

    public List<Address> getDeliveryAddresses() {
        return requireNonNullElse(this.deliveryAddresses, emptyList());
    }

}
