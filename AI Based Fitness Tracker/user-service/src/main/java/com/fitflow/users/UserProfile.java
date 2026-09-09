package com.fitflow.users;
import jakarta.persistence.*;import java.time.Instant;
@Entity @Table(name="user_profiles") public class UserProfile { @Id private String id; @Column(nullable=false,unique=true) private String email; private String firstName;private String lastName;private String role;private Instant createdAt=Instant.now(); protected UserProfile(){} public UserProfile(String id,String email,String firstName,String lastName,String role){this.id=id;this.email=email;this.firstName=firstName;this.lastName=lastName;this.role=role;} public String getId(){return id;}public String getEmail(){return email;}public String getFirstName(){return firstName;}public String getLastName(){return lastName;}public String getRole(){return role;}public Instant getCreatedAt(){return createdAt;} }

