package com.invoices.client;

import com.invoices.client.domain.Address;
import com.invoices.client.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByNameAndSurname(String name, String surname);

    boolean existsByNameAndSurnameAndAddress(String name, String surname, Address address);
}
