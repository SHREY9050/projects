package com.project.fitness.controller;

import com.project.fitness.dto.LoginRequest;
import com.project.fitness.dto.LoginResponse;
import com.project.fitness.dto.RegisterRequest;
import com.project.fitness.dto.UserResponse;
import com.project.fitness.model.User;
import com.project.fitness.security.JwtUtils;
import com.project.fitness.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.project.fitness.repository.UserRepository;

@RestController
@RequestMapping("/api/auth/")
//@AllArgsConstructor      // https://www.notion.so/04-Building-REST-API-User-Registration-Endpoint-with-Spring-Boot-3730f92c139c80e29abed7e06321e0b9?source=copy_link#3730f92c139c80819fbff1e141adc514
@RequiredArgsConstructor // Generates a constructor with only final fields and fields annotated with @NonNull
public class AuthController {

    private final UserService userService;
    private final UserRepository userRespository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

//    public AuthController(UserService userService) {  // LOMBOK WILL BE TAKEN CARE OF THIS ARG CONSTRUCTOR
//        this.userService = userService;
//    }


    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.register(registerRequest));
        // or  return ResponseEntity.ok(userService.register(registerRequest));
    }



    // By def, all endpoint including (/signin) also are required SIGN IN Auth secured by Spring Security
    // But /login  must be publicly accessible by all. To remove we must change in SecurityConfig.java
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        // https://app.notion.com/p/25-User-Login-API-Building-Secure-SignIn-Endpoint-with-JWT-3790f92c139c802bbc94ff9e9b281bfe?source=copy_link
        //Once the request has been authenticated, the Authentication will usually be stored in a thread-local SecurityContext
        // Authentication  authentication;


        try{

            User user = userService.authenticate(loginRequest);

            String token = jwtUtils.generateToken(user.getId(), user.getRole().name());

            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(token , userService.mapToResponse(user)));

        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(401).build();
        }


    }


}
