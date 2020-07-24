package com.invoices.client.api;

import com.invoices.client.ClientService;
import com.invoices.client.api.requests.RegisterClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdateClientRequest;
import com.invoices.client.domain.Client;
import com.invoices.client.dto.AddressDto;
import com.invoices.client.dto.ClientQueryDto;
import com.invoices.client.exceptions.AttemptToAddCompanyClientWithAlreadyExistingNip;
import com.invoices.client.exceptions.AttemptToAddExistingPersonClient;
import com.invoices.client.exceptions.AttemptToUpdateNonExistingClientException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@AllArgsConstructor
@RequestMapping("/client")
public class ClientEndpoints {
    private final ClientService clientService;

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<Client> register(@RequestBody RegisterClientRequest request)
            throws AttemptToAddCompanyClientWithAlreadyExistingNip, AttemptToAddExistingPersonClient {
        return new ResponseEntity<>(this.clientService.registerClient(request), HttpStatus.OK);
    }

    @PostMapping("/remove")
    public Long remove(RemoveClientRequest request) {
        return this.clientService.removeClient(request);
    }

    @PutMapping("/update")
    public Client updateClient(UpdateClientRequest request) throws AttemptToUpdateNonExistingClientException {
        return this.clientService.updateClient(request);
    }

    @GetMapping(value = "/filter", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Client>> query(@RequestParam(required = false) Long id,
                                              @RequestParam(required = false) String companyName,
                                              @RequestParam(required = false) String nip,
                                              @RequestParam(required = false) String vat,
                                              @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String surname,
                                              @RequestParam(required = false) String dateOfBirth,
                                              @RequestParam(required = false) AddressDto address,
                                              @RequestParam(required = false) String phoneNumber,
                                              @RequestParam(required = false) String fax) {

        ClientQueryDto clientDto = ClientQueryDto.builder()
                .id(id)
                .companyName(companyName)
                .nip(nip)
                .vat(vat)
                .name(name)
                .surname(surname)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .phoneNumber(phoneNumber)
                .fax(fax)
                .build();

        return new ResponseEntity<>(this.clientService.filterClients(clientDto), HttpStatus.OK);
    }
}
