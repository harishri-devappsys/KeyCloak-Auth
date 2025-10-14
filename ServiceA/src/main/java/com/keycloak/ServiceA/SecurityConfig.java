package com.keycloak.ServiceA;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        // Allow anyone to access the "/call-connector" endpoint
                        .requestMatchers("/call-connector").permitAll()
                        // Any other request that might be added later should be authenticated
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
