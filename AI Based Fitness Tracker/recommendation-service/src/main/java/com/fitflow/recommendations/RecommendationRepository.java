package com.fitflow.recommendations;import java.util.*;import org.springframework.data.mongodb.repository.MongoRepository;public interface RecommendationRepository extends MongoRepository<Recommendation,String>{List<Recommendation> findByUserIdOrderByCreatedAtDesc(String userId);}

