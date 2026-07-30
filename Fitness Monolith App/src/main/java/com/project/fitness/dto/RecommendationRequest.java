package com.project.fitness.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {

    private String userId;
    private String activityId;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;

//    private String type;
//    private String recommendation;
    // Intentionally we are not setting these 2 field
}
