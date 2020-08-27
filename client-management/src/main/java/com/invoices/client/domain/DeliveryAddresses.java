package com.invoices.client.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Set;

@Entity
@Builder
@Data
public class DeliveryAddresses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    private Set<Address> deliveryAddr;
}
