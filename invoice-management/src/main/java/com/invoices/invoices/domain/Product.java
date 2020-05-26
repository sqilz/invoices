package com.invoices.invoices.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNullElse;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String code;
    private String description;
    private double price;
    private int discount;

    @ElementCollection
    private List<String> attributes;

    public List<String> getAttributes() {
        return requireNonNullElse(this.attributes, emptyList());
    }

    @Builder
    public Product(String name, String code, String description, double price, int discount, List<String> attributes) {
        this.name = name;
        this.code = code;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.attributes = attributes;
    }
}
