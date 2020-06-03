package com.invoices.client;

import com.invoices.client.api.requests.AddCompanyClientRequest;
import com.invoices.client.api.requests.AddPersonClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdateCompanyClientRequest;
import com.invoices.client.api.requests.UpdatePersonClientRequest;
import com.invoices.client.domain.Client;
import com.invoices.client.domain.Company;
import com.invoices.client.domain.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Builder
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public Long addCompanyClient(AddCompanyClientRequest request) {
        Company company = (Company) this.clientRepository.save(Company.builder()
                .name(request.getName())
                .contactPeople(request.getContactPeople())
                .deliveryAddresses(request.getDeliveryAddresses())
                .build());

        log.info("Company successfully added!");

        return company.getId();
    }

    public Long addPersonClient(AddPersonClientRequest request) {
        Person person = (Person) this.clientRepository.save(Person.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .deliveryAddresses(request.getDeliveryAddresses())
                .build());

        log.info("Company successfully added!");

        return person.getId();
    }

    @SneakyThrows
    public Long removeClient(RemoveClientRequest request) {
        Client client = (Client) this.clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalStateException("no such client"));

        this.clientRepository.delete(client);

        log.info("Company removed");
        return client.getId();
    }

    public Person updatePersonClient(UpdatePersonClientRequest request) {

        return 1L;
    }

    public Company updateCompanyClient(UpdateCompanyClientRequest request) {

        return 1L;
    }
}
