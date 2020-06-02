package com.invoices.client;

import com.invoices.client.api.requests.AddClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdateClientRequest;
import com.invoices.client.domain.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Builder
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Long addClient(AddClientRequest request) {
        Client client = this.clientRepository.save(Client.builder()
                .companyName(request.getCompanyName())
                .contactPersons(request.getContactPersons())
                .deliveryAddresses(request.getDeliveryAddresses())
                .build());

        log.info("Client successfully added!");

        return client.getClientId();
    }

    public Long removeClient(RemoveClientRequest request) {
        Client client = this.clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalStateException("no such client"));

        this.clientRepository.delete(client);

        log.info("Client removed");
        return client.getClientId();
    }

    public Long updateClient(UpdateClientRequest request) {

        return 1L;
    }
}
