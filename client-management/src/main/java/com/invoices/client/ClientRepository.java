package com.invoices.client;

import com.invoices.client.domain.Client;
import com.invoices.client.domain.Company;
import com.invoices.client.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository<T extends Client> extends JpaRepository<T, Long> {

    Optional<T> findById(Long id);

    Optional<Company> findByNip(String nip);

    boolean existsByNip(String nip);

    Optional<Person> findByNameAndSurname(String name, String surname);

    boolean existsByNameAndSurnameAndAddress(String name, String surname, String address);
}
