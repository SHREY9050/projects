package com.fitness.userservice.dto;


import com.fitness.userservice.model.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email format")
    private String email;

    private String keycloakId;


    @NotBlank(message = "Password is Required")
    @Size(min = 6, message = "Password must have atleast 6 characters")
    private String password;

    private String firstName;
    private String lastName;
}
