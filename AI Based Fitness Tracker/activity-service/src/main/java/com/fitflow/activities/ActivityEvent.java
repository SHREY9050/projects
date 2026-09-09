package com.fitflow.activities;import java.time.Instant;public record ActivityEvent(String activityId,String userId,String type,int duration,int caloriesBurned,Integer steps,Double distanceKm,Integer averageHeartRate,Double paceMinutesPerKm,Instant startTime){}

