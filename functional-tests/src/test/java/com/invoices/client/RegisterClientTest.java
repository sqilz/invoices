package com.invoices.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invoices.client.requests.RegisterClientRequest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class RegisterClientTest {
    private static final String host = "http://localhost:8080/client";
    RegisterClientRequest.Address address = RegisterClientRequest.Address.builder()
            .country("test")
            .street("s")
            .houseNumber("2")
            .zipCode("s")
            .city("test")
            .build();
    RegisterClientRequest.Address address2 = RegisterClientRequest.Address.builder()
            .country("test")
            .street("two")
            .houseNumber("2")
            .zipCode("s")
            .city("test")
            .build();

    @Test
    void registerClientCompany() throws JsonProcessingException {

        RegisterClientRequest build = RegisterClientRequest.builder()
                .companyName("test")
                .nip(UUID.randomUUID().toString())
                .vat("test")
                .country("test")
                .address(address)
                .phoneNumber(UUID.randomUUID().toString())
                .fax("test")
                .deliveryAddresses(List.of(address, address2))
                .build();

        String req = new ObjectMapper().writeValueAsString(build);
        System.out.println(req);
        given()
                .contentType(ContentType.JSON)
                .body(req)
                .post(host + "/register")
                .then()
                .statusCode(200);
    }
}
