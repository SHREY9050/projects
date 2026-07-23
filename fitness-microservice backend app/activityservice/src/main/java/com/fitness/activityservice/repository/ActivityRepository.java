package com.fitness.activityservice.repository;

import com.fitness.activityservice.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> {
}



/**
 * ⚖️ When to Choose Which
 * Use JpaRepository (SQL) when:
 * You need complex relationships (joins, foreign keys).
 * Transactions and ACID compliance are critical.
 * Schema must be strict and validated.
 * Example: Banking, ERP, e-commerce.
 *
 * Use MongoRepository (NoSQL) when:
 * You need flexible schema (documents evolve easily).
 * High scalability and fast reads/writes matter.
 * No complex joins are required.
 * Example: IoT data, logs, analytics, user profiles.*/

// Yes, both are Spring Data repositories that give you CRUD operations and query methods, but they target different storage engines.
//  How They Differ -
//      => JpaRepository → works with relational databases like MySQL, PostgreSQL, etc. via JPA/Hibernate.
//      => MongoRepository → works with MongoDB (NoSQL).

/**
 *
 * 📚 Example Usage
 * --------------------------------------------
 * JPA (Relational DB) => java       ---------------------------------------------
 *
 *      @Entity
 *      @Table(name = "users")
 *       public class User {
 *          @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
 *          private Long id;
 *          private String name;
 *      }
 *
 *      public interface UserRepository extends JpaRepository<User, Long> {
 *          List<User> findByName(String name);
 *      }
 *
 *
 *MongoDB (NoSQL) => java           ---------------------------------------------------
 *
 *      @Document(collection = "users")
 *      public class User {
 *          @Id
 *          private String id;
 *          private String name;
 *      }
 *
 *      public interface UserRepository extends MongoRepository<User, String> {
 *          List<User> findByName(String name);
 *      }
 *
 *
 */
