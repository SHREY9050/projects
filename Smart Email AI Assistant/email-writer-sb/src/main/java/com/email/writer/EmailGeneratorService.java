package com.email.writer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



import java.util.Map;

@Service
public class EmailGeneratorService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")   // @Value is a Spring annotation used for injecting values into fields, methods, or constructor parameters and it lets you pull values from property files, environment variables, or even inline expressions.
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;


    /*
        ⚡ WebClient
            What it is: A fully built, ready-to-use HTTP client.
            Usage: You call methods like .get(), .post(), .retrieve() directly.
            Scope: Best when you just need a simple client with default settings.

        🛠️ WebClient.Builder
            What it is: A factory/configurator for creating WebClient instances.
            Usage: Lets you customize base URLs, default headers, filters, codecs, timeouts, etc.
            Scope: Best when you need multiple clients with different configurations, or want to inject a preconfigured client via Spring Boot.
    **/
    public EmailGeneratorService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public String generateEmailReply(EmailRequest emailRequest){


        // Build the prompt
        String prompt = buildPrompt(emailRequest);

        // Craft a request
        Map<String,Object> requestBody = Map.of(
            "contents", new Object[] {
                    Map.of("parts", new Object[] {
                           Map.of("text", prompt)
                           })
                }
        );
        // Do request and get response
        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // Extract Response and  Return
        return extractResponseContent(response);
    }

    private String extractResponseContent(String response) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);
            return  rootNode.path("candidates")
                            .get(0)
                            .path("content")
                            .path("parts")
                            .get(0)
                            .path("text")
                            .asText();

        } catch (Exception e) {
            return "Error processing request: "+ e.getMessage();
        }
    }

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate a professional email reply for the following email content. Please don't generate a subject line ");

        if(emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()){
            prompt.append("Use ").append(emailRequest.getTone()).append(" tone.");
        }

        prompt.append("\nOriginal: \n").append(emailRequest.getEmailContent());
        System.out.println(prompt);

        return  prompt.toString();
    }
}
