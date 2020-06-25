package com.invoices.client;

import com.invoices.client.api.requests.AddCompanyClientRequest;
import com.invoices.client.api.requests.AddPersonClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdateCompanyClientRequest;
import com.invoices.client.api.requests.UpdatePersonClientRequest;
import com.invoices.client.domain.Client;
import com.invoices.client.domain.Company;
import com.invoices.client.domain.Person;
import com.invoices.client.exceptions.AttemptToAddCompanyClientWithAlreadyExistingNip;
import com.invoices.client.exceptions.AttemptToAddExistingPersonClient;
import com.invoices.client.exceptions.AttemptToRemoveNonExistingClientException;
import com.invoices.client.exceptions.AttemptToUpdateNonExistingClientException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Builder
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;
    private final PersonRepository personRepository;

    @Transactional
    public String addCompanyClient(AddCompanyClientRequest request) throws AttemptToAddCompanyClientWithAlreadyExistingNip {
        if (this.companyRepository.existsByNip(request.getNip())) {
            throw new AttemptToAddCompanyClientWithAlreadyExistingNip();
        }

        Company company = Company.companyBuilder()
                .nip(request.getNip())
                .name(request.getName())
                .contactPeople(request.getContactPeople())
                .deliveryAddresses(request.getDeliveryAddresses())
                .build();

        this.companyRepository.save(company);
        log.info("Company successfully added!" + " " + company.toString());

        return company.getName();
    }

    public String addPersonClient(AddPersonClientRequest request) throws AttemptToAddExistingPersonClient {
        if (this.personRepository.existsByNameAndSurnameAndAddress(request.getName(), request.getSurname(), request.getAddress())) {
            throw new AttemptToAddExistingPersonClient();
        }

        Person person = Person.personBuilder()
                .name(request.getName())
                .surname(request.getSurname())
                .address(request.getAddress())
                .phoneNumber(request.getPhoneNumber())
                .deliveryAddresses(request.getDeliveryAddresses())
                .build();

        this.personRepository.save(person);
        log.info("Company successfully added!");

        return person.getName() + " " + person.getSurname();
    }

    @SneakyThrows
    public Long removeClient(RemoveClientRequest request) {
        Client client = this.clientRepository.findById(request.getClientId())
                .orElseThrow(AttemptToRemoveNonExistingClientException::new);

        this.clientRepository.delete(client);

        log.info("Company removed");
        return client.getId();
    }

    @Transactional
    public Person updatePersonClient(UpdatePersonClientRequest request) throws AttemptToUpdateNonExistingClientException {
        Person person = this.personRepository.findById(request.getId())
                .orElseThrow(AttemptToUpdateNonExistingClientException::new);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(request, person);

        this.personRepository.save(person);

        return person;
    }

    public Company updateCompanyClient(UpdateCompanyClientRequest request) throws AttemptToUpdateNonExistingClientException {
        Company company = this.companyRepository.findById(request.getClientId())
                .orElseThrow(AttemptToUpdateNonExistingClientException::new);

        this.companyRepository.save(company);

        return company;
    }
}
