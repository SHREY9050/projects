package com.project.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Data  // Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY) // If u fetch User it won't Fetch Activity until u explicitly fetch it
    @JsonIgnore //           Column name → user_id  , Foreign key constraint name → fk_activity_user
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_activity_user"))
    private User user;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    // https://copilot.microsoft.com/shares/yv76mfZZrdoMY8JQ2EZsw
    @JdbcTypeCode(SqlTypes.JSON)              //  will convert the incoming data into JSON & JSON from the DB will be converted back into a Map when loading.
    @Column(columnDefinition = "json")        // “This column must be created as type JSON in the database.”
                                              // Hibernate implements that by generating the proper SQL statement: Ensures the database column is physically typed as JSON (instead of plain VARCHAR or TEXT).
    private Map<String , Object> additionalMetrics;

    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "activity", cascade =  CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Recommendation> recommendations = new ArrayList<>();



}
