package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

// This class will consume the API of 'User Service' and based on that generate the DATA

@Service
@RequiredArgsConstructor
@Slf4j                          // logger annotation -> to view logs and identify what is going on
public class UserValidationService {

    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {

        log.info("####################################################################\n" +
                "Calling User Service for {}", userId);

        boolean result = false;
        try {
            result =  userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            return result;

        } catch (WebClientResponseException e) {
            e.printStackTrace();
        }finally{
            log.info("Validation attempt for userId={} completed. Result={}", userId, result);
        }


        return false;
    }

}



//  .bodyToMono(...) is a Spring WebFlux method used when you’re making an HTTP call with
//  WebClient and you want the response body mapped into a reactive type (Mono<T>).

//  bodyToMono(Class<T>) → tells WebClient:
//   “Take the HTTP response body, convert it into an object of type T, and
//    wrap it inside a Mono<T>.”

//      A Mono<T> represents a future value that will be emitted once the entire reactive pipeline completes.
//      Even if the server has already sent the response body, the Mono won’t hand you the value until:
//      The HTTP call finishes,
//             The body is fully deserialized into the target type (Boolean, UserResponse, etc.),
//             The publisher signals completion.

/**
 * 🔎 Step‑by‑step Explanation:
 * userServiceWebClient.get()
 * → Starts building a GET request with WebClient.

 * .uri("/api/users/{userId}/validate", userId)
 * → Expands the {userId} placeholder with the actual userId value.
 * → Final URI looks like /api/users/123/validate.

 * .retrieve()
 * → Executes the HTTP call and prepares to handle the response.

 * .bodyToMono(Boolean.class)
 * → Tells WebClient: “Take the response body, deserialize it into a Boolean, and wrap it in a Mono<Boolean>.”
 * → This is reactive — it doesn’t give you the value immediately, it gives you a publisher.

 * .block()
 * → Subscribes to the Mono and waits until the value is available.
 * → Returns the actual Boolean (true/false) to your method.
 * → This makes the method synchronous.
 * */

//  EXAMPLE
//      When you type a GET URL in Postman (like http://localhost:8080/api/users/123/validate),
//          Postman sends the HTTP request, waits for the response, and shows you the body.

//      With WebClient, the same thing happens automatically inside your code:

//      Postman → you manually type the URL, hit send, and see the response.
//      WebClient → you programmatically build the request (.get().uri(...)), send it
//      (.retrieve()), and then consume the response (.bodyToMono(...)).