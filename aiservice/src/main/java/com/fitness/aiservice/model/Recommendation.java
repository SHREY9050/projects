package com.fitness.aiservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "recommendation")
@Data
@Builder
public class Recommendation {
    @Id
    private String id;
    private String activityId;
    private String userId;
    private String type;
    private String recommendation;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}


/**
 *  They work with both Spring Data JPA and Spring Data MongoDB (depending on whether you enable
 *      @EnableJpaAuditing or @EnableMongoAuditing).
 * */