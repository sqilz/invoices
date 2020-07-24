package com.invoices.client;

import com.invoices.client.api.requests.RegisterClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdateClientRequest;
import com.invoices.client.domain.Address;
import com.invoices.client.domain.Client;
import com.invoices.client.exceptions.AttemptToAddCompanyClientWithAlreadyExistingNip;
import com.invoices.client.exceptions.AttemptToAddExistingPersonClient;
import com.invoices.client.exceptions.AttemptToRemoveNonExistingClientException;
import com.invoices.client.exceptions.AttemptToUpdateNonExistingClientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ClientServiceTest {
    private static final Long ID = 123L;
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
            .houseNumber(String.valueOf(new Random().nextInt() * -1))
            .zipCode(ZIP)
            .country("PL")
            .build();
    private static final String RANDOM_ID = randomUUID().toString();

    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private AddressRepository addressRepository;

    @BeforeEach
    void setUp() {
        this.clientService = ClientService.builder()
                .clientRepository(this.clientRepository)
                .addressRepository(this.addressRepository)
                .build();
    }

    @Test
    void shouldRegisterNewCompanyClient() throws Exception {
        // given
        final String nip = randomUUID().toString();
        final RegisterClientRequest request = RegisterClientRequest.builder()
                .companyName(COMPANY_NAME)
                .vat("0")
                .nip(nip)
                .phoneNumber(RANDOM_ID)
                .fax(RANDOM_ID)
                .address(ADDRESS)
                .deliveryAddresses(Set.of(ADDRESS))
                .build();

        Client expected = Client.builder()
                .companyName(COMPANY_NAME)
                .vat("0")
                .nip(nip)
                .phoneNumber(RANDOM_ID)
                .fax(RANDOM_ID)
                .address(ADDRESS)
                .deliveryAddresses(Set.of(ADDRESS))
                .build();

        given(this.addressRepository.findAddressByCityAndStreetAndHouseNumber(anyString(), anyString(), anyString()))
                .willReturn(Optional.of(ADDRESS));

        // when
        Client client = this.clientService.registerClient(request);

        // then
        assertThat(client).isNotNull();
        assertThat(client).isEqualTo(expected);
    }

    @Test
    void shouldRegisterNewPersonClient() throws Exception {
        // given
        RegisterClientRequest request = RegisterClientRequest.builder()
                .name(RANDOM_ID)
                .surname(RANDOM_ID)
                .dateOfBirth("12-12-2000")
                .phoneNumber(RANDOM_ID)
                .address(ADDRESS)
                .deliveryAddresses(Set.of(ADDRESS))
                .build();

        Client expected = Client.builder()
                .name(RANDOM_ID)
                .surname(RANDOM_ID)
                .dateOfBirth("12-12-2000")
                .phoneNumber(RANDOM_ID)
                .address(ADDRESS)
                .deliveryAddresses(Set.of(ADDRESS))
                .build();

        given(this.addressRepository.findAddressByCityAndStreetAndHouseNumber(anyString(), anyString(), anyString()))
                .willReturn(Optional.of(ADDRESS));

        // when
        Client client = this.clientService.registerClient(request);

        // then
        assertThat(client).isNotNull();
        assertThat(client).isEqualTo(expected);
    }

    @Test
    void shouldRegisterCompanyWithExistingAddress() throws Exception {
        final String nip = randomUUID().toString();
        final String nip2 = randomUUID().toString();
        final RegisterClientRequest firstClient = RegisterClientRequest.builder()
                .companyName(RANDOM_ID)
                .vat("0")
                .nip(nip)
                .phoneNumber(RANDOM_ID)
                .fax(RANDOM_ID)
                .address(ADDRESS)
                .deliveryAddresses(Set.of(ADDRESS))
                .build();

        final RegisterClientRequest existingAddress = RegisterClientRequest.builder()
                .companyName(RANDOM_ID)
                .vat("0")
                .nip(nip2)
                .phoneNumber(RANDOM_ID)
                .fax(RANDOM_ID)
                .address(ADDRESS)
                .deliveryAddresses(Set.of(ADDRESS))
                .build();

        given(this.addressRepository.findAddressByCityAndStreetAndHouseNumber(anyString(), anyString(), anyString()))
                .willReturn(Optional.of(ADDRESS));

        // register client (adds an address)
        Client registerClient = this.clientService.registerClient(firstClient);

        // when
        Client clientWithExistingAddress = this.clientService.registerClient(existingAddress);

        // then
        assertThat(clientWithExistingAddress).isNotNull();
        assertThat(clientWithExistingAddress.getAddress().getId()).isEqualTo(registerClient.getAddress().getId());
        assertThat(clientWithExistingAddress.getDeliveryAddresses().stream()
                .findFirst().get().getId()).isEqualTo(registerClient.getAddress().getId());
    }

    @Test
    void shouldNotRegisterDuplicateCompanyClient() {
        // given
        RegisterClientRequest request = RegisterClientRequest.builder()
                .nip("test")
                .build();
        when(this.clientRepository.existsByNip(anyString())).thenReturn(true);
        // when
        assertThrows(AttemptToAddCompanyClientWithAlreadyExistingNip.class,
                () -> this.clientService.registerClient(request));
    }

    @Test
    void shouldNotRegisterDuplicatePersonClient() {
        // given
        RegisterClientRequest request = RegisterClientRequest.builder()
                .name(NAME)
                .surname(SURNAME)
                .phoneNumber("test")
                .build();

        when(this.clientRepository.existsByNameAndSurnameAndPhoneNumber(anyString(), anyString(), anyString()))
                .thenReturn(true);

        // when
        assertThrows(AttemptToAddExistingPersonClient.class,
                () -> this.clientService.registerClient(request));
    }

    @Test
    void shouldUpdateExistingClient() throws AttemptToUpdateNonExistingClientException {
        // given
        Client person = Client.builder()
                .name("test")
                .surname("test")
                .address(ADDRESS)
                .deliveryAddresses(Set.of(ADDRESS))
                .build();

        when(this.clientRepository.findById(anyLong())).thenReturn(Optional.of(person));

        UpdateClientRequest request = UpdateClientRequest.builder()
                .id(1L)
                .name(NAME)
                .surname(SURNAME)
                .phoneNumber(PHONE_NUMBER)
                .address(ADDRESS)
                .deliveryAddresses(Set.of(ADDRESS))
                .build();
        // when
        Client personClient = this.clientService.updateClient(request);

        // then
        assertThat(personClient).isNotNull();
        assertThat(personClient.getId()).isEqualTo(1L);
        assertThat(personClient.getName()).isEqualTo(NAME);
        assertThat(personClient.getSurname()).isEqualTo(SURNAME);
        assertThat(personClient.getPhoneNumber()).isEqualTo(PHONE_NUMBER);
        assertThat(personClient.getAddress()).isEqualTo(ADDRESS);
        assertThat(personClient.getDeliveryAddresses()).isEqualTo(Set.of(ADDRESS));
    }

    @Test
    void shouldThrowWhenTryingToUpdateNonExistingPersonClient() {
        // given
        final Random random = new Random();
        UpdateClientRequest request = UpdateClientRequest.builder()
                .id(random.nextLong())
                .address(Address.builder().build())
                .phoneNumber(String.valueOf(random.nextInt()))
                .build();

        // when
        assertThrows(AttemptToUpdateNonExistingClientException.class,
                () -> this.clientService.updateClient(request));
    }

    @Test
    void shouldThrowWhenTryingToUpdateNonExistingCompanyClient() {
        // given
        final Random random = new Random();
        UpdateClientRequest request = UpdateClientRequest.builder()
                .id(random.nextLong())
                .address(Address.builder().build())
                .phoneNumber(String.valueOf(random.nextInt()))
                .build();

        // when
        assertThrows(AttemptToUpdateNonExistingClientException.class,
                () -> this.clientService.updateClient(request));
    }

    @Test
    void shouldRemoveExistingClient() {
        // given
        final long clientId = new Random().nextLong();
        RemoveClientRequest request = RemoveClientRequest.builder()
                .clientId(clientId)
                .build();

        when(this.clientRepository.findById(anyLong())).thenReturn(Optional.of(Client.builder().id(clientId).build()));

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
