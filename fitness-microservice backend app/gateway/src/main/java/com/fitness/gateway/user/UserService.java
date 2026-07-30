package com.fitness.gateway.user;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

// This class will consume the API of 'User Service' and based on that generate the DATA

@Service
@RequiredArgsConstructor
@Slf4j                          // logger annotation -> to view logs and identify what is going on
public class UserService {

    private final WebClient userServiceWebClient;

    public Mono<Boolean> validateUser(String userId) {

        log.info("####################################################################\n" +
                "Calling User Service for {}", userId);



            return   userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .onErrorResume(WebClientResponseException.class, e -> {
                        if(e.getStatusCode() == HttpStatus.NOT_FOUND)
                            return Mono.error(new RuntimeException("User Not Found: " + userId));
                        else if(e.getStatusCode() == HttpStatus.BAD_REQUEST)
                            return Mono.error(new RuntimeException("Invalid: " + userId));

                        return Mono.error(new RuntimeException("Unexpected Error: " +userId));

                    });



    }


    public Mono<UserResponse> registerUser(RegisterRequest registerRequest) {
        log.info("Calling User Registration for {}", registerRequest.getEmail());



        return   userServiceWebClient.post()
                .uri("/api/users/register")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // string value
                .bodyValue(registerRequest)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                     if(e.getStatusCode() == HttpStatus.BAD_REQUEST)
                        return Mono.error(new RuntimeException("Bad request: " + e.getMessage()));

                    return Mono.error(new RuntimeException("Unexpected Error: " + e.getMessage()));

                });
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