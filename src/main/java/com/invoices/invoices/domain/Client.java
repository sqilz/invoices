package com.invoices.invoices.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElse;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String companyName;

    @OneToMany
    List<Person> contactPersons;

    @OneToMany
    List<Address> deliveryAddresses;

    @Builder
    public Client(String companyName, List<Person> contactPersons, List<Address> deliveryAddresses) {
        this.companyName = companyName;
        this.contactPersons = contactPersons;
        this.deliveryAddresses = deliveryAddresses;
    }

    public List<Person> getContactPersons() {
        return requireNonNullElse(this.contactPersons, emptyList());
    }

    public List<Address> getDeliveryAddresses() {
        return requireNonNullElse(this.deliveryAddresses, emptyList());
    }

    public String getCompanyName() {
        return requireNonNullElse(this.companyName, "-");
    }
}
