package com.invoices.client.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    // TODO: query + generic query + clients?vat=xxx clients?vat=xxx&nip=yyyy - INDEXES!!!
    //
    String companyName;
    String nip;
    String vat;

    String name;
    String surname;
    String dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL)
    Address address;
    String phoneNumber;
    String fax;

    @OneToMany
    private DeliveryAddresses deliveryAddresses;
}
