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
import java.util.List;

@Entity
@Data  // Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY) // If u fetch User it won't Fetch Recommendation until u explicitly fetch it
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recommendation_user"))
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) // If u fetch User it won't Fetch Recommendation until u explicitly fetch it
    @JoinColumn(name = "activity_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recommendation_activity"))
    @JsonIgnore
    private Activity activity;

    private String type;

    // @Column() annotation help to make changes in ColumnField
    // @Column(columnDefinition = "") -> It controls the datatype of the specific column in the generated SQL schema. ;
    // @Column(length = ) -> increase length of column -> Specifically adjusts the size of VARCHAR when Hibernate maps a String.

    @Column(length = 2000)
    private String recommendation; // By default, Hibernate maps a String field to a VARCHAR(255) column in the database.
                                    // To make it more length

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> improvements;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> suggestions;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private List<String> safety;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;



}
