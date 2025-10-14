package com.keycloak.ServiceA;


// Example service to make the call

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
class RelayController {

    @Autowired
    private WebClient webClient;

    @GetMapping("/call-connector")
    public String callConnector() {
        // WebClient will automatically get a JWT from Keycloak and add it as a Bearer token
        return this.webClient
                .get()
                .uri("http://localhost:8082/api/data") // URL of Service B
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}