package com.fitness.aiservice.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

//  “We’re using the same Activity class here because the consumer needs to deserialize the producer’s data into a Java
//  object, and this class provides the mapping.”

public class Activity {

    private String id;
    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

    @Field("metrics")                                       // Annotation coming from MongoDB dependency
    private Map<String, Object> additionalMetrics;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;



}

/**
 * We are using Activity Class here also, because the Data coming from Producer need to be de-serialize,
 * SO THAT WE CAN MAP THAT Data with this Activity Class
 * */