package com.fitness.userservice.repository;


import com.fitness.userservice.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {


    boolean existsByEmail(String email);

    Boolean existsByKeycloakId(String userId);

    User findByEmail(@NotBlank(message = "Email is Required") @Email(message = "Invalid Email format") String email);


    // Hibernate is that much level of powerful which automatically understand what developer want.
    // Even for custom query without writing any query
    
    
}




/** https://app.notion.com/p/04-Building-REST-API-User-Registration-Endpoint-with-Spring-Boot-3730f92c139c80e29abed7e06321e0b9?source=copy_link
 *
 * -> By declaring "JpaRepository<User, String>",you bind this repository specifically to the `User` entity and its primary key type (`String`).
 * -> All repository methods (`save()`, `findById()`, `delete()`, etc.) are type‑safe and restricted to the `User` entity.
 * -> That means this repository can only perform operations on the `User` table mapped by the `User` entity.
 *
 * */