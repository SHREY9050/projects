package com.fitness.userservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")    // Name of the table -> users
@Data
@NoArgsConstructor        // JPA/Hibernate instantiate entities via reflection.
                          // They need a blank shell object (new Entity()) before populating fields from the database.
public class User {

    @Id
    @GeneratedValue(strategy =  GenerationType.UUID)
    private String id;

    private String keycloakId;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;  // Default Role -> USER

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

/**
 *
 ** These are defined by the Jakarta Persistence API (JPA). Hibernate just implements them: **
 * ---------------------------------------------------------------------------------------------------
 * @Entity → Marks a class as a JPA entity.
 * @Table(name = "users") → Maps the entity to a specific DB table.
 * @Id → Marks the primary key field.
 * @GeneratedValue(strategy = GenerationType.UUID) → New in Jakarta Persistence 3.2 (2024+). Generates UUIDs as primary keys.
 * @GeneratedValue(strategy = GenerationType.AUTO) → Lets the provider pick the best numeric strategy based on DB dialect.
 * @Column(unique = true) → Adds a uniqueness constraint.
 * @Column(nullable = false) → Adds a NOT NULL constraint.
 * @Enumerated(EnumType.STRING) → Persists enums as their string names (instead of ordinal numbers).
 *
 * -----------------------------------------------------------
 * These are not part of JPA, but Hibernate adds them: (vendor-specific features).
 *----------------------------------------------------------
 * @CreationTimestamp → Auto-populates a field with the timestamp when the row is first inserted.
 * @UpdateTimestamp → Auto-updates a field with the timestamp whenever the row changes.
 * */
