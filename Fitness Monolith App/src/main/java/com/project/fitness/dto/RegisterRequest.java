package com.project.fitness.dto;

import com.project.fitness.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email") // The string has to be a well-formed email address.
                                      // Exact semantics of what makes up a valid email address are left to Jakarta Validation providers. Accepts CharSequence. null elements are considered valid.
    private String email;

    @NotBlank(message = "Password is Required")
    private String password;

    private String firstName;
    private String lastName;
    private UserRole role;
}
