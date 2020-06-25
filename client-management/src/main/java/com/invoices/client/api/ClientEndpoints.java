package com.invoices.client.api;

import com.invoices.client.ClientService;
import com.invoices.client.api.requests.AddCompanyClientRequest;
import com.invoices.client.api.requests.AddPersonClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdateCompanyClientRequest;
import com.invoices.client.api.requests.UpdatePersonClientRequest;
import com.invoices.client.domain.Company;
import com.invoices.client.domain.Person;
import com.invoices.client.exceptions.AttemptToAddCompanyClientWithAlreadyExistingNip;
import com.invoices.client.exceptions.AttemptToAddExistingPersonClient;
import com.invoices.client.exceptions.AttemptToUpdateNonExistingClientException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/client")
public class ClientEndpoints {
    private final ClientService clientService;

    @GetMapping("/test")
    public String myMethod() {
        return "test";
    }

    @PostMapping("/add-company")
    public String addClient(AddCompanyClientRequest request) throws AttemptToAddCompanyClientWithAlreadyExistingNip {
        return this.clientService.addCompanyClient(request);
    }

    @PostMapping("/add-person")
    public String addClient(AddPersonClientRequest request) throws AttemptToAddExistingPersonClient {
        return this.clientService.addPersonClient(request);
    }

    @PostMapping("/remove")
    public Long removeClient(RemoveClientRequest request) {
        return this.clientService.removeClient(request);
    }

    @PutMapping("/update-company")
    public Company updateClient(UpdateCompanyClientRequest request) throws AttemptToUpdateNonExistingClientException {
        return this.clientService.updateCompanyClient(request);
    }

    @PutMapping("/update-person")
    public Person updateClient(UpdatePersonClientRequest request) throws AttemptToUpdateNonExistingClientException {
        return this.clientService.updatePersonClient(request);
    }
}
