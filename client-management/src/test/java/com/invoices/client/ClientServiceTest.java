package com.invoices.client;

import com.invoices.client.api.requests.AddClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdateClientRequest;
import com.invoices.client.domain.Address;
import com.invoices.client.domain.Client;
import com.invoices.client.domain.Person;
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
import static org.mockito.ArgumentMatchers.anyLong;
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

    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    private static final String COMPANY_NAME = "COMPANY_NAME";

    @BeforeEach
    void setUp() {
        this.clientService = ClientService.builder().clientRepository(this.clientRepository).build();
    }

    @Test
    void shouldRegisterNewClient() {
        // given
        AddClientRequest request = AddClientRequest.builder()
                .companyName(COMPANY_NAME)
                .contactPersons(List.of(Person.builder()
                        .name(NAME)
                        .surname(SURNAME)
                        .phoneNumber(PHONE_NUMBER)
                        .address(Address.builder()
                                .houseNumber(Integer.toString(1))
                                .city(CITY)
                                .street(STREET)
                                .zipCode(ZIP)
                                .build())
                        .build()))
                .deliveryAddresses(List.of(Address.builder()
                        .houseNumber("2A")
                        .street(STREET)
                        .city(CITY)
                        .zipCode(ZIP)
                        .build()))
                .build();

        // when
        Long clientId = this.clientService.addClient(request);

        // then
        MatcherAssert.assertThat(clientId, isNotNull());

    }

    @Test
    void shouldNotRegisterDuplicateClient() {
        // given
        when(this.clientService)
        // when

        // then
    }

    @Test
    void shouldUpdateExistingClient() {
        // given
        Long clientId = 1L;
        when(this.clientRepository.findById(clientId)).thenReturn(Optional.of(Client.builder().build()));

        UpdateClientRequest request = UpdateClientRequest.builder()
                .clientId(clientId)
                .name(NAME)
                .surname(SURNAME)
                .phoneNumber(PHONE_NUMBER)
                .address(Address.builder()
                        .city(CITY)
                        .street(STREET)
                        .houseNumber(Integer.toString(1))
                        .zipCode(ZIP)
                        .build())
                .deliveryAddress(Address.builder()
                        .city(CITY)
                        .street(STREET)
                        .houseNumber(Integer.toString(1))
                        .zipCode(ZIP)
                        .build())
                .build();
        // when
        Long updatedClientId = this.clientService.updateClient(request);

        // then
        assertThat(updatedClientId, isNotNull());
        assertThat(updatedClientId, equalTo(clientId));
    }

    @Test
    void shouldThrowWhenTryingToUpdateNonExistingClient() {
        // given
        UpdateClientRequest request = UpdateClientRequest.builder()
                .clientId(new Random().nextLong())
                .build();

        // when
        Assertions.assertThrows(AttemptToUpdateNonExistingClientException.class,
                () -> this.clientService.updateClient(request));
    }

    @Test
    void shouldRemoveExistingClient() {
        // given
        long clientId = new Random().nextLong();
        RemoveClientRequest request = RemoveClientRequest.builder()
                .clientId(clientId)
                .build();

        when(this.clientRepository.findById(anyLong())).thenReturn(Optional.of(Client.builder().build()));

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
