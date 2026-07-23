package com.fitness.activityservice.service;
import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final KafkaTemplate<String, Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;       //  topicName = "activity-events"


    public ActivityResponse trackActivity(ActivityRequest request) {

       boolean isValidUser = userValidationService.validateUser(request.getUserId());

       if(!isValidUser){
           throw new RuntimeException("Invalid User: " + request.getUserId());
       }

        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .additionalMetrics(request.getAdditionalMetrics())
                .startTime(request.getStartTime())
                .build();

        Activity savedActivity = activityRepository.save(activity);

        try{
// SYNTAX-  kafkaTemplate.send(topicName, KEY, DATA);
            kafkaTemplate.send(topicName, savedActivity.getUserId(), savedActivity);

        }catch(Exception e){
            e.printStackTrace();
        }


        return maptoResponse(savedActivity);
    }

    private ActivityResponse maptoResponse(Activity savedActivity) {


        ActivityResponse response = new ActivityResponse();
               response.setId(savedActivity.getId());
               response.setUserId(savedActivity.getUserId());
               response.setType(savedActivity.getType());
               response.setDuration(savedActivity.getDuration());
               response.setCaloriesBurned(savedActivity.getCaloriesBurned());
               response.setStartTime(savedActivity.getStartTime());
               response.setAdditionalMetrics(savedActivity.getAdditionalMetrics());
               response.setCreatedAt(savedActivity.getCreatedAt());
               response.setUpdatedAt(savedActivity.getUpdatedAt());

//  YOU CAN USE BUILDER PATTERN ALSO -> ADD @Builder ANNOTATION IN ActivityResponse
//        ActivityResponse response = ActivityResponse.builder()
//                .id(savedActivity.getId())
//                .userId(savedActivity.getUserId())
//                .type(savedActivity.getType())
//                .duration(savedActivity.getDuration())
//                .caloriesBurned(savedActivity.getCaloriesBurned())
//                .additionalMetrics(savedActivity.getAdditionalMetrics())
//                .startTime(savedActivity.getStartTime())
//                .createdAt(savedActivity.getCreatedAt())
//                .updatedAt(savedActivity.getUpdatedAt())
//                .build();


        return response;
    }
}
