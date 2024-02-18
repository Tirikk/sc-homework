package com.dontirikk.addressservice.web;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/address")
@SecurityRequirement(name = "basicAuth")
public class AddressController {
    public static final String ADDRESS = "Budapest, Komor Marcell u. 1, 1095";

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Address getAddress() {
        return new Address(UUID.randomUUID(), ADDRESS);
    }

    public record Address(UUID id, String address) {}
}
