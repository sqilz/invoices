package com.invoices.client.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElse;

@NoArgsConstructor
@Data
@MappedSuperclass
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String name;
    @OneToOne(cascade = CascadeType.PERSIST)
    Address address;
    String phoneNumber;

    @OneToMany(cascade = CascadeType.PERSIST)
    @ElementCollection
    private List<Address> deliveryAddresses = new ArrayList<>();

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
