package com.invoices.client;

import com.invoices.client.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findAddressByCityAndStreetAndHouseNumber(String city, String street, String houseNumber);
}
