package com.invoices.client.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyContacts {
    private String name;
    private String surname;
    private String phoneNumber;
}
