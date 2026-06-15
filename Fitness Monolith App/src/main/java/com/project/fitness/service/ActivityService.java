package com.project.fitness.service;


import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.model.Activity;
import com.project.fitness.model.User;
import com.project.fitness.repository.ActivityRepository;
import com.project.fitness.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;


    public ActivityResponse trackActivity(ActivityRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Invalid User ID "+ request.getUserId()));

        Activity activity = Activity.builder()
                .user(user)  // user -> bring full user obj
                .type(request.getType())
                .additionalMetrics(request.getAdditionalMetrics())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity savedActivity) {
        ActivityResponse activityResponse = new ActivityResponse();
            activityResponse.setUserId(savedActivity.getUser().getId());
            activityResponse.setId(savedActivity.getId());
            activityResponse.setType(savedActivity.getType());
            activityResponse.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
            activityResponse.setDuration(savedActivity.getDuration());
            activityResponse.setCaloriesBurned(savedActivity.getCaloriesBurned());
            activityResponse.setStartTime(savedActivity.getStartTime());

            return activityResponse;
    }


    public List<ActivityResponse> getUserActivities(String userId) {

        // In actual activityRepository doesn't hold UserId -> so this is a custom method created in interface
        List<Activity> activityList = activityRepository.findByUserId(userId);

        return activityList.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }
}
