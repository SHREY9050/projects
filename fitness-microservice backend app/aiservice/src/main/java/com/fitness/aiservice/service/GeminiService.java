package com.fitness.aiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

// This Class is used to send request to GEMINI and return Response

@Service
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")         // Take Values from YAML FILE
    private String geminiApiUrl;

    @Value("${gemini.api.key}")         // Take Values from YAML FILE
    private String geminiApiKey;

    // Initializing Web Client with the help of Constructor
    public GeminiService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.build();
    }

    public String getRecommendations(String details){

        Map<String, Object> requestBody = Map.of(
                "contents", new Object[] {
                        Map.of(
                                "parts", new Object[] {
                                        Map.of(
                                                "text", details
                                        )
                                }
                        )
                }
        );

        String response = webClient.post()
                .uri(geminiApiUrl)
                .header("Content-Type","application/json")
                .header("X-goog-api-key", geminiApiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();
/**
        Sending the request
            webClient.post() → POST request.
            .uri(geminiApiUrl) → target URL.
            .header("X-goog-api-key", geminiApiKey) → API key in header.
            .bodyValue(requestBody) → attach JSON body.
            .retrieve().bodyToMono(String.class).block() → synchronously wait for the response as a String.

        Returning the response
            The raw JSON response from Gemini is returned as a String.
*/
        return response;

    }

}


/**
 ----------------------------------------
 // Empty map
 ----------------------------------------
Map<String, String> emptyMap = Map.of();


 ----------------------------------------
 // Single entry
 ---------------------------------------
Map<String, Integer> oneEntry = Map.of("A", 1);


 ----------------------------------------
 // Multiple entries
 -----------------------------------------------
Map<String, String> multi = Map.of(
        "name", "Shrey",
        "role", "Developer",
        "city", "Bareilly"
);


 ----------------------------------------
 // More than 10 entries → use Map.ofEntries
 ----------------------------------------------
Map<String, Integer> bigMap = Map.ofEntries(
        Map.entry("A", 1),
        Map.entry("B", 2),
        Map.entry("C", 3),
        Map.entry("D", 4)
);
*/