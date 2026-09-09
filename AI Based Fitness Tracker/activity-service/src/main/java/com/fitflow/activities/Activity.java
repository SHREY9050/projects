package com.fitflow.activities;
import java.time.*;import java.util.*;import org.springframework.data.annotation.Id;import org.springframework.data.mongodb.core.mapping.Document;
@Document("activities") public class Activity{@Id public String id;public String userId;public String type;public int duration;public int caloriesBurned;public Integer steps;public Double distanceKm;public Integer averageHeartRate;public Double paceMinutesPerKm;public Instant startTime;public Map<String,Object> additionalMetrics=new HashMap<>();public Instant createdAt=Instant.now();}

