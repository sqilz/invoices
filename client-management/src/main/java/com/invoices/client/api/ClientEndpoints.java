package com.invoices.client.api;


import com.invoices.client.ClientService;
import com.invoices.client.api.requests.AddClientRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/client")
public class ClientEndpoints {
    private final ClientService clientService;

    @PostMapping("/add")
    public Long addClient(AddClientRequest addClientRequest) {
        return this.clientService.addClient(addClientRequest);
    }

    @PostMapping("/remove")
    public Long removeClient(Long id) {
        return this.clientService.removeClient(id);
    }

    @PutMapping("/update")
    public Long updateClient() {
        return this.clientService.updateClient();
    }
}
