package com.fitness.activityservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced  // Normally, a WebClient or RestTemplate points to a fixed URL like http://localhost:8080.
                   // With @LoadBalanced, you can use the service name instead of a hardcoded host/port.
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder){
        return webClientBuilder.baseUrl("http://USER-SERVICE")
                .build();
    }
}


/**
 * HOW THIS WORKS -
 *
 * Activity Service =>
 *       Also runs as a Eureka Client (it connects to Eureka Server).
 *       When you call http://USERSERVICE/... via WebClient, the @LoadBalanced bean intercepts the call.
 *       Instead of treating USERSERVICE as a literal hostname, it asks Eureka:
 *       “Give me the actual host:port instances for USERSERVICE.”
 * */

/**
 *
 * WebClient knows the baseUrl is an external service.
 * @LoadBalanced lets you use the service name instead of hardcoding host/port.
 * Eureka Client (fetch-registry=true) ensures Activity Service has the registry of all services.
 * Your job in Activity Service is simply to fetch from Eureka and let the LoadBalancer resolve USERSERVICE into a real instance.
 * */