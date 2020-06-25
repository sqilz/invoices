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
import com.invoices.client.exceptions.AttemptToAddCompanyClientWithAlreadyExistingNip;
import com.invoices.client.exceptions.AttemptToAddExistingPersonClient;
import com.invoices.client.exceptions.AttemptToRemoveNonExistingClientException;
import com.invoices.client.exceptions.AttemptToUpdateNonExistingClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ClientServiceTest {
    private static final String COMPANY_NAME = "COMPANY_NAME";
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
    private static final String RANDOM_ID = randomUUID().toString();

    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        this.clientService = ClientService.builder()
                .clientRepository(this.clientRepository)
                .companyRepository(this.companyRepository)
                .personRepository(this.personRepository)
                .build();
    }

    @Test
    void shouldRegisterNewCompanyClient() throws Exception {
        // given
        AddCompanyClientRequest request = AddCompanyClientRequest.builder()
                .name(COMPANY_NAME)
                .nip(RANDOM_ID)
                .phoneNumber(RANDOM_ID)
                .contactPeople(List.of(CompanyContacts.builder()
                        .name(NAME)
                        .surname(SURNAME)
                        .phoneNumber(PHONE_NUMBER)
                        .build()))
                .deliveryAddresses(List.of(ADDRESS))
                .build();

        // when
        String clientId = this.clientService.addCompanyClient(request);

        // then
        assertThat(clientId).isNotNull();
        assertThat(clientId).isEqualTo(COMPANY_NAME);

    }

    @Test
    void shouldRegisterNewPersonClient() throws Exception {
        // given
        AddPersonClientRequest request = AddPersonClientRequest.builder()
                .name(NAME)
                .surname(SURNAME)
                .address(ADDRESS)
                .phoneNumber(PHONE_NUMBER)
                .deliveryAddresses(List.of(ADDRESS))
                .build();

        // when
        String clientId = this.clientService.addPersonClient(request);

        // then
        assertThat(clientId).isNotNull();
        assertThat(clientId).isEqualTo(NAME + " " + SURNAME);
    }

    @Test
    void shouldNotRegisterDuplicateCompanyClient() {
        // given
        AddCompanyClientRequest request = AddCompanyClientRequest.builder()
                .nip("test")
                .build();
        when(this.companyRepository.existsByNip(anyString())).thenReturn(true);
        // when
        assertThrows(AttemptToAddCompanyClientWithAlreadyExistingNip.class,
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

        when(this.personRepository.existsByNameAndSurnameAndAddress(anyString(), anyString(), any()))
                .thenReturn(true);

        // when
        assertThrows(AttemptToAddExistingPersonClient.class,
                () -> this.clientService.addPersonClient(request));
    }

    @Test
    void shouldUpdateExistingPersonClient() throws AttemptToUpdateNonExistingClientException {
        // given
        Person person = Person.personBuilder()
                .name("test")
                .surname("test")
                .address(ADDRESS)
                .deliveryAddresses(List.of(ADDRESS))
                .build();

        when(this.personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        UpdatePersonClientRequest request = UpdatePersonClientRequest.builder()
                .id(1L)
                .name(NAME)
                .surname(SURNAME)
                .phoneNumber(PHONE_NUMBER)
                .address(ADDRESS)
                .deliveryAddress(List.of(ADDRESS))
                .build();
        // when
        Person personClient = this.clientService.updatePersonClient(request);

        // then
        assertThat(personClient).isNotNull();
        assertThat(personClient.getId()).isEqualTo(1L);
        assertThat(personClient.getName()).isEqualTo(NAME);
        assertThat(personClient.getSurname()).isEqualTo(SURNAME);
        assertThat(personClient.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(personClient.getAddress()).isEqualTo(ADDRESS);
        assertThat(personClient.getDeliveryAddresses()).isEqualTo(List.of(ADDRESS));
    }

    @Test
    void shouldThrowWhenTryingToUpdateNonExistingPersonClient() {
        // given
        UpdatePersonClientRequest request = UpdatePersonClientRequest.builder()
                .id(new Random().nextLong())
                .build();

        // when
        assertThrows(AttemptToUpdateNonExistingClientException.class,
                () -> this.clientService.updatePersonClient(request));
    }

    @Test
    void shouldThrowWhenTryingToUpdateNonExistingCompanyClient() {
        // given
        UpdateCompanyClientRequest request = UpdateCompanyClientRequest.builder()
                .clientId(new Random().nextLong())
                .build();

        // when
        assertThrows(AttemptToUpdateNonExistingClientException.class,
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
        assertThat(removedClientId).isNotNull();
    }

    @Test
    void shouldThrowWhenTryingToRemoveNonExistingClient() {
        // given
        Long clientId = new Random().nextLong();
        RemoveClientRequest request = RemoveClientRequest.builder()
                .clientId(clientId)
                .build();
        // when
        assertThrows(AttemptToRemoveNonExistingClientException.class,
                () -> this.clientService.removeClient(request));
    }
}
