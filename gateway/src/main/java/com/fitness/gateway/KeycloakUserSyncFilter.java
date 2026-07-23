package com.fitness.gateway;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import com.fitness.gateway.user.RegisterRequest;
import com.fitness.gateway.user.UserService;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.text.ParseException;

@Component
@Slf4j
@RequiredArgsConstructor

public class KeycloakUserSyncFilter implements WebFilter {

    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String userId = exchange.getRequest().getHeaders().getFirst("X-User-ID");
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        RegisterRequest registerRequest = getUserDetails(token);
        if(userId == null){
            userId = registerRequest.getKeycloakId();
        }

        if(userId != null && token != null){
            String finalUserId = userId;
            return userService.validateUser(userId)
            // flatMap() is one of the core operators in Spring WebFlux.
                    // It’s used heavily because WebFlux is reactive and asynchronous, and you often need to chain calls that return Mono or Flux.
                    .flatMap(exist -> {     // exist -> get  true/false
                        if(!exist){             // If user not exis , register it otherwise return an empty string via else block
                            if(registerRequest != null){
                                return userService.registerUser(registerRequest)
                                        .then(Mono.empty());
                            }else{
                                return Mono.empty();
                            }
                        }else{
                            log.info("User already exist. So skipping sync");
                            return Mono.empty();
                        }
                    })
                    .then(Mono.defer(() -> {                 // defer -> tells dont run this block until above bloc will not completes
                                               // uses to forward request with adding header so that if any internal service using User-ID so it can use it
                        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                                .header("X-User-ID", finalUserId)
                                .build();
                        // mutatedRequest = getting/extracting value of header "X-User-Id"
                                return chain.filter(exchange.mutate().request(mutatedRequest).build());
                            }));


        }

        return chain.filter(exchange);

    }

    private RegisterRequest getUserDetails(String token) {

/**        SignedJWT parsed = SignedJWT.parse(token); // break into header/payload/signature
 JWTClaimsSet claims = parsed.getJWTClaimsSet(); // read data  */

        try {
            String tokenWithoutBearer = token.replace("Bearer", "").trim();
            SignedJWT signedJWT = SignedJWT.parse(tokenWithoutBearer);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();

            RegisterRequest request = new RegisterRequest();
            request.setEmail(claims.getStringClaim("email"));
            request.setKeycloakId(claims.getStringClaim("sub"));
            request.setPassword(claims.getStringClaim("preferred_username"));
            request.setFirstName(claims.getStringClaim("given_name"));
            request.setLastName(claims.getStringClaim("family_name"));

            return request;

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}

/**
In Spring, a WebFilter is a reactive interface used in WebFlux to intercept and process HTTP requests before they
 reach your controllers, similar to servlet filters in Spring MVC but designed for non-blocking, reactive pipelines.
 It’s commonly used for cross-cutting concerns like authentication, logging, CORS, or request transformations.

 🔎ServerWebExchange
 Definition: Represents the entire HTTP request–response interaction in WebFlux.

 🔎 WebFilterChain
 Definition: Represents the chain of filters that the request flows through.


 */
