package com.project.fitness.controller;

import com.project.fitness.dto.ActivityRequest;
import com.project.fitness.dto.ActivityResponse;
import com.project.fitness.model.Activity;
import com.project.fitness.service.ActivityService;
import com.project.fitness.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest){
        return ResponseEntity.status(HttpStatus.OK).body(activityService.trackActivity(activityRequest));
    }

    // https://www.notion.so/09-Rest-API-Retrieving-Activities-Using-Custom-Request-Headers-3740f92c139c805894ced2f29c0410a0?source=copy_link
    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(
            @RequestHeader(value = "X-User-ID") String userId
            // Look for a header named X-User-ID in the incoming HTTP request and bind its value to the userId parameter.
            // We add manually Header via POSTMAN na d due to this annotation Spring Boot will retrieve it and use in code
    ){
        return ResponseEntity.status(HttpStatus.OK).body(activityService.getUserActivities(userId));
    }


}
