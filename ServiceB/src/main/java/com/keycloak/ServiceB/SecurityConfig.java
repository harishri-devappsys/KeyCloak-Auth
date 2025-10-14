// In ServiceB/SecurityConfig.java
package com.keycloak.ServiceB;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                // Be explicit: Configure the resource server to validate JWTs
                .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}