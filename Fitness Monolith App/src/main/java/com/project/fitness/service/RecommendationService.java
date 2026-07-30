package com.project.fitness.service;

import com.project.fitness.dto.RecommendationRequest;
import com.project.fitness.model.Activity;
import com.project.fitness.model.Recommendation;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.RecommendationRepository;
import com.project.fitness.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public Recommendation generateRecommendation(RecommendationRequest recommendationRequest) {

        // recommendationRequest.getId() give input in field which ACTUAL USER GIVE
        User user = userRepository.findById(recommendationRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User Not Found " + recommendationRequest.getUserId()));

        Activity activity = activityRepository.findById(recommendationRequest.getActivityId())
                .orElseThrow(() -> new RuntimeException("Activity Not Found " + recommendationRequest.getUserId()));

        Recommendation recommendation = Recommendation.builder()
                                .user(user)
                                .activity(activity)
                                .improvements(recommendationRequest.getImprovements())
                                .safety(recommendationRequest.getSafety())
                                .suggestions(recommendationRequest.getSuggestions())
                                .build();

        return recommendationRepository.save(recommendation);
    }

    public List<Recommendation> getUserRecommendation(String userId) {

        //  List<Recommendation> recommendationList = recommendationRepository.findById(userId);
        // return recommendationList;

        return recommendationRepository.findByUserId(userId);

    }

    public List<Recommendation> getActivityRecommendation(String activityId) {

        return recommendationRepository.findByActivityId(activityId);
    }
}
