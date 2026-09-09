package com.fitflow.recommendations;
import java.util.*;import org.springframework.kafka.annotation.KafkaListener;import org.springframework.stereotype.Service;
@Service public class RecommendationListener {private final RecommendationRepository repo;private final GeminiCoach coach;public RecommendationListener(RecommendationRepository r,GeminiCoach c){repo=r;coach=c;}@KafkaListener(topics="activity-events",groupId="fitflow-recommendations") public void consume(ActivityEvent event){Recommendation r=coach.coach(event);repo.save(r);}}

