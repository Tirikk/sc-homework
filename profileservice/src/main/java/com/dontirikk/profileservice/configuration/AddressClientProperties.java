package com.dontirikk.profileservice.configuration;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("address-client")
public class AddressClientProperties {
    private String baseUrl;
    private String username;
    @ToString.Exclude
    private String password;
}
