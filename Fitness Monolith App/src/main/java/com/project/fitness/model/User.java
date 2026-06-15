package com.project.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data  // Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "fitness_user")  // Default behavior: If you don’t specify @Table, Hibernate uses the class name (User) as the table name.
                               // Customization: @Table(name = "fitness_user") lets you avoid reserved keywords (user is reserved in many databases like MySQL) and makes the schema more descriptive
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true)
    private String email;
    private String password;
    private String firstName;
    private String lastName;


    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.USER;
    // Default value -> hardcore value of any user creation will have User Role


    @CreationTimestamp // this annotation coming from Hibernate
                            // Specifies that the annotated field of property is a generated creation timestamp.
                            // The timestamp is generated just once, when an entity instance is inserted in the database
    private LocalDateTime createdAt;

    @UpdateTimestamp  // this annotation coming from Hibernate
                      // Specifies that the annotated field of property is a generated update timestamp.
                      // The timestamp is regenerated every time an entity instance is updated in the database.
    private LocalDateTime updatedAt;

    // As we saw in DB Design :
    // User has OnetoMany relationship with Activity
    // User has OnetoMany relationship with Recommendation

    // https://copilot.microsoft.com/shares/96qHVumnzW2QGCbS2zYbc
    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore  // - With `@JsonIgnore`, the field is **completely sealed off from JSON**. - That means:- No input** → Jackson won’t set it from JSON. - No output** → Jackson won’t include it in JSON responses.
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Recommendation> recommendations = new ArrayList<>();

//    public User(){   // Due to Lombok we don't need to create manually
//
//    }
}



