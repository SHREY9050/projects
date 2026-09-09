package com.fitflow.activities;import java.time.Instant;import java.util.*;import org.springframework.data.mongodb.repository.MongoRepository;public interface ActivityRepository extends MongoRepository<Activity,String>{List<Activity> findByUserIdOrderByStartTimeDesc(String userId);List<Activity> findByUserIdAndStartTimeGreaterThanEqual(String userId,Instant start);}

