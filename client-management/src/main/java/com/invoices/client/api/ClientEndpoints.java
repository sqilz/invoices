package com.invoices.client.api;


import com.invoices.client.ClientService;
import com.invoices.client.api.requests.AddCompanyClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdatePersonClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/client")
public class ClientEndpoints {
    private final ClientService clientService;

    @PostMapping("/add")
    public Long addClient(AddCompanyClientRequest request) {
        return this.clientService.addClient(request);
    }

    @PostMapping("/remove")
    public Long removeClient(RemoveClientRequest request) {
        return this.clientService.removeClient(request);
    }

    @PutMapping("/update")
    public Long updateClient(UpdatePersonClientRequest request) {
        return this.clientService.updateClient(request);
    }
}
