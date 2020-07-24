package com.invoices.client;

import com.invoices.client.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByNip(String nip);

    boolean existsByNameAndSurnameAndPhoneNumber(String name, String surname, String phoneNumber);
}
