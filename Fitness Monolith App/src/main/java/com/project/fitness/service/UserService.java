package com.project.fitness.service;

import com.project.fitness.dto.*;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import com.project.fitness.model.UserRole;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(RegisterRequest request) {
        UserRole role = request.getRole() != null ? request.getRole()
                : UserRole.USER;

        User user = User.builder()              // User.builder() call is essentially mimicking
                .email(request.getEmail())      //  an all‑arguments constructor — but in a more flexible, readable way
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(role)
                .build();   // rest field considered as null

//        User user = new User(
//                null,
//                request.getEmail(),
//                request.getPassword(),
//                request.getFirstName(),
//                request.getLastName(),
//                Instant.parse("2026-06-02T16:27:56.783Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
//                Instant.parse("2026-06-02T16:27:56.783Z").atZone(ZoneOffset.UTC).toLocalDateTime(),
//                List.of(),
//                List.of()
//        );


        User savedUser = userRepository.save(user); // Params: entities –> must not be null nor must it contain null. Returns: the saved entities.
        return mapToResponse(savedUser);
    }

    public UserResponse mapToResponse(User savedUser) {
        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());

        return response;

    }

    public User authenticate(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail());

        if(user == null) {
            throw new RuntimeException("Invalid Crdentials");
        };

        //     First param -> rawPassword and Second param -> encodePassword
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid Crdentials");
        return user;
    }
}
