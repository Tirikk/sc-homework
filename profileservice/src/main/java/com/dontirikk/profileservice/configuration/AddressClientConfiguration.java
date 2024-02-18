package com.dontirikk.profileservice.configuration;

import com.dontirikk.profileservice.web.client.AddressClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class AddressClientConfiguration {

    @Bean
    public AddressClient addressClient(AddressClientProperties clientProps) {
        var webClient = WebClient.builder()
                .baseUrl(clientProps.getBaseUrl())
                .defaultHeaders(httpHeaders -> httpHeaders.setBasicAuth(clientProps.getUsername(), clientProps.getPassword()))
                .build();

        var httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient))
                .build();

        return httpServiceProxyFactory.createClient(AddressClient.class);
    }
}
