package com.invoices.client.api.requests;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateClientRequest {
    private Long id;
}
