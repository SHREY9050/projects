package com.project.fitness.repository;

import com.project.fitness.model.Recommendation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation , String> {

    /*
        Key Point
Manual retrieval: If you wanted to fetch Activity first and then its recommendations, you’d inject ActivityRepository, load the activity, and then navigate its relationship. That’s more verbose.
Custom JPA method: By defining findByActivityId, Spring Data JPA directly queries the recommendation table for rows where the activity_id column matches.
Requirement: The column (activity_id) must exist in the Recommendation table (as a foreign key to Activity). Otherwise, JPA won’t know how to generate the query.
    */

    /*
        Because your repository method signature is List<Recommendation>, Hibernate ensures the final result is a List.
        That’s why you can directly return it without needing .toList() — the conversion is already handled.
    */
    List<Recommendation> findByUserId(String userId);
    // Hibernate is that much level of powerful which automatically understand what developer want.
    // Even for custom query without writing any query

    List<Recommendation> findByActivityId(String activityId);
}
