package com.project.fitness.controller;

import com.project.fitness.dto.RecommendationRequest;
import com.project.fitness.model.Recommendation;
import com.project.fitness.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    // This endpoint will save the Recommendation
    @PostMapping("/generate")
    public ResponseEntity<Recommendation> generateRecommendation(@RequestBody RecommendationRequest recommendationRequest){

        Recommendation recommendation = recommendationService.generateRecommendation(recommendationRequest);
        return ResponseEntity.status(HttpStatus.OK).body(recommendation);
    }

    // This endpoint will help to retrieve the Recommendation saved for a User
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable String userId){
        List<Recommendation> recommendationList = recommendationService.getUserRecommendation(userId);
        return ResponseEntity.status(HttpStatus.OK).body(recommendationList);
//        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.getUserRecommendation(userId));
    }

    // Retrieving Recommendation for specific Activity
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<Recommendation>> getActivityRecommendation(@PathVariable String activityId){
        List<Recommendation> recommendationList = recommendationService.getUserRecommendation(activityId);
        return ResponseEntity.status(HttpStatus.OK).body(recommendationList);
//        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.getActivityRecommendation(activityId));
    }

}
