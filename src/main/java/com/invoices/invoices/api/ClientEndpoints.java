package com.invoices.invoices.api;

import com.invoices.invoices.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@AllArgsConstructor
@RequestMapping("/client")
public class ClientEndpoints {
    private final ClientService clientService;

    @PostMapping("/add")
    public String addClient() {
        return this.clientService.addClient();
    }

    @PostMapping("/remove")
    public String removeClient() {
        this.clientService.removeClient();
        return "ok";
    }

    @PutMapping("/update")
    public String updateClient() {
        this.clientService.updateClient();
        return "ok";
    }
}
