package com.fitness.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http){

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)  // csrf -> disabled
                .authorizeExchange(exchange -> exchange.anyExchange().authenticated())  // any coming request need to get authenticated
                .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults())) // Reference Below
                .build();
    }
}

// CSRF = “Is this request really from my user?” (per‑request authenticity). => Server issues + validates CSRF tokens


/**
oauth2ResourceServer(...) → tells Spring Security: “This application is a Resource Server.”
        oauth.jwt(...) → specifies that the Resource Server will validate JWT tokens (instead of opaque tokens).
            Customizer.withDefaults() → applies the default JWT validation behavior:

        Reads the Authorization: Bearer <token> header.
        Fetches the public key (from the Authorization Server’s jwks_uri).
        Validates the JWT signature, expiration, and claims.
        Rejects invalid tokens with 401 Unauthorized.

 */