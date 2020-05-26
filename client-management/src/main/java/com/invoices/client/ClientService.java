package com.invoices.client;

import com.invoices.client.api.requests.AddClientRequest;
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

    public Long addClient(AddClientRequest addClientRequest) {
        Client save = this.clientRepository.save(Client.builder()
                .companyName(addClientRequest.getCompanyName())
                .contactPersons(addClientRequest.getContactPersons())
                .deliveryAddresses(addClientRequest.getDeliveryAddresses())
                .build());

        log.info("Client successfully added!");

        return save.getId();
    }

    public Long removeClient(Long id) {
        Client client = this.clientRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("no such client"));

        this.clientRepository.delete(client);

        log.info("Client removed");
        return client.getId();
    }

    public Long updateClient(UpdateClientRequest updateClientRequest) {

        return 1L;
    }
}
