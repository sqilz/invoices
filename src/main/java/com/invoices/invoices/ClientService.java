package com.invoices.invoices;

import com.invoices.invoices.api.requests.AddClientRequest;
import com.invoices.invoices.domain.Client;
import org.springframework.stereotype.Service;


@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public String addClient(AddClientRequest addClientRequest) {
        this.clientRepository.save(Client.builder()
                .companyName(addClientRequest.getCompanyName())
                .contactPersons(addClientRequest.getContactPersons())
                .deliveryAddresses(addClientRequest.getDeliveryAddresses())
                .build());

        return "client-id";
    }

    public void removeClient() {

    }

    public void updateClient() {

    }


}
