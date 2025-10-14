package com.keycloak.ServiceB;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConnectorController {

    @GetMapping("/data")
    public String getData() {
        return "Secure data from Connector Service!";
    }
}