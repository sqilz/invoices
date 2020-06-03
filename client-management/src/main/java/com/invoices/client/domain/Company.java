package com.invoices.client.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElse;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Company extends Client {
    String nip;

    @OneToMany
    List<CompanyContacts> contactPeople;

    @Builder
    public Company(String name, Address address, String phoneNumber, List<Address> deliveryAddresses, String nip, List<CompanyContacts> contactPeople) {
        super(name, address, phoneNumber, deliveryAddresses);
        this.nip = nip;
        this.contactPeople = contactPeople;
    }

    public List<CompanyContacts> getContactPeople() {
        return requireNonNullElse(this.contactPeople, emptyList());
    }
}
