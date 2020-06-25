package com.invoices.client.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElse;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Company extends Client {
    String nip;
    // vat
    // TODO: look for fields that company can use
    @OneToMany(cascade = CascadeType.PERSIST)
    @ElementCollection
    List<CompanyContacts> contactPeople = new ArrayList<>();

    @Builder(builderMethodName = "companyBuilder")
    public Company(String name, Address address, String phoneNumber,
                   List<Address> deliveryAddresses, String nip, List<CompanyContacts> contactPeople) {
        super(name, address, phoneNumber, deliveryAddresses);
        this.nip = nip;
        this.contactPeople = contactPeople;
    }

    public List<CompanyContacts> getContactPeople() {
        return requireNonNullElse(this.contactPeople, emptyList());
    }
}
