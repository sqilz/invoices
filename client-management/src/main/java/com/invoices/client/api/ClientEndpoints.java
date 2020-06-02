package com.invoices.client.api;


import com.invoices.client.ClientService;
import com.invoices.client.api.requests.AddClientRequest;
import com.invoices.client.api.requests.RemoveClientRequest;
import com.invoices.client.api.requests.UpdateClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/client")
public class ClientEndpoints {
    private final ClientService clientService;

    @PostMapping("/add")
    public Long addClient(AddClientRequest request) {
        return this.clientService.addClient(request);
    }

    @PostMapping("/remove")
    public Long removeClient(RemoveClientRequest request) {
        return this.clientService.removeClient(request);
    }

    @PutMapping("/update")
    public Long updateClient(UpdateClientRequest request) {
        return this.clientService.updateClient(request);
    }
}
