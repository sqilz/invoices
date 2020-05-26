package com.invoices.client;

import com.invoices.client.api.requests.AddClientRequest;
import com.invoices.client.api.requests.UpdateClientRequest;
import com.invoices.client.domain.Address;
import com.invoices.client.domain.Client;
import com.invoices.client.domain.Person;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ClientServiceTest {
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
        AddClientRequest addClientRequest = AddClientRequest.builder()
                .companyName(COMPANY_NAME)
                .contactPersons(List.of(Person.builder()
                        .name("Joe")
                        .surname("Doe")
                        .phoneNumber("123418564")
                        .address(Address.builder()
                                .houseNumber("1")
                                .city("warsaw")
                                .street("mangalia")
                                .zipCode("05-500")
                                .build())
                        .build()))
                .deliveryAddresses(List.of(Address.builder()
                        .houseNumber("2")
                        .street("test")
                        .city("warsaw")
                        .zipCode("05-500")
                        .build()))
                .build();

        // when
        Long clientId = this.clientService.addClient(addClientRequest);

        // then
        MatcherAssert.assertThat(clientId, isNotNull());

    }

    @Test
    void shouldUpdateExistingClient() {
        // given
        Long id = 1L;
        UpdateClientRequest request = UpdateClientRequest.builder()
                .id(id)
                .build();
        // when
        Long clientId = this.clientService.updateClient(request);
        // then
        assertThat(clientId, isNotNull());
        assertThat(clientId, equalTo(id));

    }

    @Test
    void shouldThrowWhenTryingToUpdateNonExistingClient() throws Exception {
        // given

        // when

        // then

    }

    @Test
    void shouldRemoveExistingClient() {
        // given
        Client client = Client.builder()
                .companyName("name")
                .build();
        when(this.clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

        // when
        Long clientId = this.clientService.removeClient(1L);

        // then
        assertThat(clientId, isNotNull());
    }

    @Test
    void shouldThrowWhenTryingToRemoveNonExistingClient() throws Exception {
        // given

        // when

        // then

    }
}
