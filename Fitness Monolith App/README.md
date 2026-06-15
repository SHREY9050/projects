
# 🏋️ Fitness Tracking API

## 📖 Overview
Fitness Tracking API is a **Spring Boot monolithic backend** application for fitness tracking.  
It provides RESTful endpoints for user authentication, activity logging, and personalized recommendations.  
The API is documented using **OpenAPI 3.1** and exposed via **Swagger UI**.

---

## 🚀 Features
- User registration and authentication (JWT-based)
- Activity tracking (running, walking, cycling, etc.)
- Personalized fitness recommendations
- Cloud deployment with persistent MySQL database
- Auto-generated API documentation with Swagger UI

---

## 🌐 Live Deployment
- **Swagger UI**: [https://fitness-mono-1-136j.onrender.com/swagger-ui/index.html](https://fitness-mono-1-136j.onrender.com/swagger-ui/index.html)  
- **OpenAPI JSON**: `https://fitness-mono-1-136j.onrender.com/v3/api-docs`  
- **API Base URL**: `https://fitness-mono-1-136j.onrender.com/api`

---

## 🛠️ Tech Stack
- **Backend**: Spring Boot (Web, Security, Data JPA)
- **Database**: MySQL (cloud-hosted)
- **Documentation**: SpringDoc OpenAPI Starter Webmvc UI
- **Build Tool**: Maven
- **Language**: Java 17+

---

## 🗄️ Database Setup
The app uses **MySQL** as its primary database.

### Local Development
Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fitness_db
spring.datasource.username=root
spring.datasource.password=secret
spring.jpa.hibernate.ddl-auto=update
```

### Cloud Deployment
In production, database credentials are injected via environment variables:
- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

---

## 📑 API Usage
You can test endpoints using **Postman** or `curl`.

### Example: Register User
```bash
curl -X POST https://fitness-mono-1-136j.onrender.com/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"shrey","password":"securePass"}'
```

### Example: Add Activity
```bash
curl -X POST https://fitness-mono-1-136j.onrender.com/api/activities \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"type":"Running","duration":30,"calories":250}'
```

---

## 🔒 Security
- JWT-based authentication for protected endpoints
- Swagger UI access can be restricted to authenticated users
- Example security scheme defined in OpenAPI:
```yaml
components:
  securitySchemes:
    bearerAuth:
      type: http
      scheme: bearer
      bearerFormat: JWT
security:
  - bearerAuth: []
```

---

## 📂 Project Structure
```
fitness-tracking-api/
 ├── src/main/java/com/fitness
 │    ├── controller   # REST controllers
 │    ├── service      # Business logic
 │    ├── repository   # JPA repositories
 │    ├── model        # Entities/DTOs
 │    └── config       # Security & Swagger config
 └── src/main/resources
      └── application.properties
```

---

