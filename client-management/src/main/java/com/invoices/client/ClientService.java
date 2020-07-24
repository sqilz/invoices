package com.invoices.client;

import com.invoices.client.api.requests.RegisterClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdateClientRequest;
import com.invoices.client.domain.Address;
import com.invoices.client.domain.Client;
import com.invoices.client.dto.ClientQueryDto;
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
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Strings.isNullOrEmpty;

@Slf4j
@Service
@Builder
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public Client registerClient(RegisterClientRequest request) throws AttemptToAddCompanyClientWithAlreadyExistingNip, AttemptToAddExistingPersonClient {
        if (!isNullOrEmpty(request.getNip()) && this.clientRepository.existsByNip(request.getNip())) {
            throw new AttemptToAddCompanyClientWithAlreadyExistingNip();
        }

        if (this.clientRepository.existsByNameAndSurnameAndPhoneNumber(request.getName(), request.getSurname(), request.getPhoneNumber())) {
            throw new AttemptToAddExistingPersonClient();
        }

        Set<Address> deliveryAddresses = request.getDeliveryAddresses().stream()
                .filter(f -> !f.equals(request.getAddress()))
                .map(this::findInRepositoryOrUseProvided)
                .collect(Collectors.toSet());
        deliveryAddresses.add(request.getAddress());

        Client client = Client.builder()
                .companyName(request.getCompanyName())
                .nip(request.getNip())
                .vat(request.getVat())
                .name(request.getName())
                .surname(request.getSurname())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .fax(request.getFax())
                .address(request.getAddress())
                .deliveryAddresses(deliveryAddresses)
                .build();

        this.clientRepository.save(client);
        log.info("Company successfully added!" + " " + client.toString());

        return client;
    }

    @SneakyThrows
    public Long removeClient(RemoveClientRequest request) {
        Client client = this.clientRepository.findById(request.getClientId())
                .orElseThrow(AttemptToRemoveNonExistingClientException::new);

        this.clientRepository.delete(client);

        log.info("Client removed from database");
        return client.getId();
    }

    @Transactional
    public Client updateClient(UpdateClientRequest request) throws AttemptToUpdateNonExistingClientException {
        Client client = this.clientRepository.findById(request.getId())
                .orElseThrow(AttemptToUpdateNonExistingClientException::new);

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(request, client);

        this.clientRepository.save(client);

        return client;
    }

    @Transactional(propagation = Propagation.NESTED)
    public Address findInRepositoryOrUseProvided(Address address) {

        return this.addressRepository.findAddressByCityAndStreetAndHouseNumber(
                address.getCity(), address.getStreet(), address.getHouseNumber())
                .orElse(this.addressRepository.save(address));
    }

    @Transactional
    public List<Client> filterClients(ClientQueryDto queryDto) {
        Client build = Client.builder().build();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(queryDto, build);
        Example<Client> example = Example.of(build);
        return this.clientRepository.findAll(example);
    }
}
