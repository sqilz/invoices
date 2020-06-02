package com.invoices.client;

import com.invoices.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Optional<Client> findByCompanyName(String companeName);

    Optional<Client> findById(Long clientId);

    Optional<Client> findByCompanyName();
}
