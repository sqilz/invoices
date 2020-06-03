package com.invoices.client;

import com.invoices.client.api.requests.AddCompanyClientRequest;
import com.invoices.client.api.requests.AddPersonClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdateCompanyClientRequest;
import com.invoices.client.api.requests.UpdatePersonClientRequest;
import com.invoices.client.domain.Address;
import com.invoices.client.domain.Company;
import com.invoices.client.domain.CompanyContacts;
import com.invoices.client.domain.Person;
import com.invoices.client.exceptions.AttemptToAddClientWithExistingNip;
import com.invoices.client.exceptions.AttemptToRemoveNonExistingClientException;
import com.invoices.client.exceptions.AttemptToUpdateNonExistingClientException;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ClientServiceTest {
    private static final String NAME = "Joe";
    private static final String SURNAME = "Doe";
    private static final String PHONE_NUMBER = "123418564";
    private static final String CITY = "warsaw";
    private static final String STREET = "magnalia";
    private static final String ZIP = "00-000";
    private static final Address ADDRESS = Address.builder()
            .city(CITY)
            .street(STREET)
            .houseNumber("1")
            .zipCode(ZIP)
            .build();

    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private static final String COMPANY_NAME = "COMPANY_NAME";

    @BeforeEach
    void setUp() {
        this.clientService = ClientService.builder().clientRepository(this.clientRepository).build();
    }

    @Test
    void shouldRegisterNewCompanyClient() {
        // given
        AddCompanyClientRequest request = AddCompanyClientRequest.builder()
                .name(COMPANY_NAME)
                .contactPeople(List.of(CompanyContacts.builder()
                        .name(NAME)
                        .surname(SURNAME)
                        .phoneNumber(PHONE_NUMBER)
                        .build()))
                .deliveryAddresses(List.of(Address.builder()
                        .houseNumber("2A")
                        .street(STREET)
                        .city(CITY)
                        .zipCode(ZIP)
                        .build()))
                .build();

        // when
        Long clientId = this.clientService.addCompanyClient(request);

        // then
        MatcherAssert.assertThat(clientId, isNotNull());

    }

    @Test
    void shouldRegisterNewPersonClient() {
        // given
        AddPersonClientRequest request = AddPersonClientRequest.builder()
                .name(NAME)
                .surname(SURNAME)
                .address(Address.builder().build())
                .phoneNumber(PHONE_NUMBER)
                .deliveryAddresses(List.of(Address.builder()
                        .houseNumber("2A")
                        .street(STREET)
                        .city(CITY)
                        .zipCode(ZIP)
                        .build()))
                .build();

        // when
        Long clientId = this.clientService.addPersonClient(request);

        // then
        MatcherAssert.assertThat(clientId, isNotNull());
    }

    @Test
    void shouldNotRegisterDuplicateCompanyClient() {
        // given
        AddCompanyClientRequest request = AddCompanyClientRequest.builder()
                .nip("test")
                .build();
        when(this.clientRepository.existsByNip(anyString())).thenThrow(new AttemptToAddClientWithExistingNip());
        // when
        Assertions.assertThrows(AttemptToAddClientWithExistingNip.class,
                () -> this.clientService.addCompanyClient(request));
    }

    @Test
    void shouldNotRegisterDuplicatePersonClient() {
        // given
        AddPersonClientRequest request = AddPersonClientRequest.builder()
                .name(NAME)
                .surname(SURNAME)
                .address(ADDRESS)
                .phoneNumber("test")
                .build();
        when(this.clientRepository.existsByNameAndSurnameAndAddress(anyString(), anyString(), any())).thenThrow(new AttemptToAddClientWithExistingNip());
        // when
        Assertions.assertThrows(AttemptToAddClientWithExistingNip.class,
                () -> this.clientService.addPersonClient(request));
    }

    @Test
    void shouldUpdateExistingPersonClient() {
        // given
        Long clientId = 1L;
        when(this.clientRepository.findById(clientId)).thenReturn(Optional.of(Person.builder().build()));

        UpdatePersonClientRequest request = UpdatePersonClientRequest.builder()
                .clientId(clientId)
                .name(NAME)
                .surname(SURNAME)
                .phoneNumber(PHONE_NUMBER)
                .address(ADDRESS)
                .deliveryAddress(List.of(ADDRESS))
                .build();
        // when
        Person personClient = this.clientService.updatePersonClient(request);

        // then
        assertThat(personClient, isNotNull());
        assertThat(personClient.getId(), equalTo(clientId));
        assertThat(personClient.getName(), equalTo(NAME));
        assertThat(personClient.getSurname(), equalTo(SURNAME));
        assertThat(personClient.getPhoneNumber(), equalTo(PHONE_NUMBER));
        assertThat(personClient.getAddress(), equalTo(ADDRESS));
        assertThat(personClient.getDeliveryAddresses(), equalTo(List.of(ADDRESS)));
    }

    @Test
    void shouldThrowWhenTryingToUpdateNonExistingPersonClient() {
        // given
        UpdatePersonClientRequest request = UpdatePersonClientRequest.builder()
                .clientId(new Random().nextLong())
                .build();
        when(this.clientRepository.findById(anyLong())).thenThrow(new AttemptToUpdateNonExistingClientException());

        // when
        Assertions.assertThrows(AttemptToUpdateNonExistingClientException.class,
                () -> this.clientService.updatePersonClient(request));
    }

    @Test
    void shouldThrowWhenTryingToUpdateNonExistingCompanyClient() {
        // given
        UpdateCompanyClientRequest request = UpdateCompanyClientRequest.builder()
                .clientId(new Random().nextLong())
                .build();

        when(this.clientRepository.findById(anyLong())).thenThrow(new AttemptToUpdateNonExistingClientException());

        // when
        Assertions.assertThrows(AttemptToUpdateNonExistingClientException.class,
                () -> this.clientService.updateCompanyClient(request));
    }

    @Test
    void shouldRemoveExistingClient() {
        // given
        long clientId = new Random().nextLong();
        RemoveClientRequest request = RemoveClientRequest.builder()
                .clientId(clientId)
                .build();

        when(this.clientRepository.findById(anyLong())).thenReturn(Optional.of(Company.builder().build()));

        // when
        Long removedClientId = this.clientService.removeClient(request);

        // then
        assertThat(removedClientId, isNotNull());
    }

    @Test
    void shouldThrowWhenTryingToRemoveNonExistingClient() {
        // given
        Long clientId = new Random().nextLong();
        RemoveClientRequest request = RemoveClientRequest.builder()
                .clientId(clientId)
                .build();
        // when
        Assertions.assertThrows(AttemptToRemoveNonExistingClientException.class,
                () -> this.clientService.removeClient(request));
    }
}
