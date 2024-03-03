package com.dontirikk.profileservice.web.client;

import com.dontirikk.profileservice.web.dto.AddressDTO;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@HttpExchange(
        url = "/api/v1/address",
        accept = APPLICATION_JSON_VALUE
)
public interface AddressClient {

    @GetExchange
    AddressDTO getAddress();
}
