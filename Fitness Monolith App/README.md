

# 🏋️ Fitness Tracking API v1.0

## 📖 Overview
Fitness Tracking API is a **Spring Boot monolithic backend** application for fitness tracking.  
It provides RESTful endpoints for user authentication, activity logging, and personalized recommendations.  
The API is documented using **OpenAPI 3.1** and exposed via **Swagger UI**.

---

## 🚀 Features
- User registration and authentication (JWT-based)
- Activity tracking (running, walking, cycling, etc.)
- Personalized fitness recommendations
- Cloud deployment on **Render**
- Persistent database hosted on **Neon PostgreSQL**
- Auto-generated API documentation with Swagger UI

---

## 🌐 Live Deployment
- **Swagger UI**: [https://fitness-mono-1-136j.onrender.com/swagger-ui/index.html](https://fitness-mono-1-136j.onrender.com/swagger-ui/index.html)  
- **OpenAPI JSON**: `https://fitness-mono-1-136j.onrender.com/v3/api-docs`  
- **API Base URL**: `https://fitness-mono-1-136j.onrender.com/api`

---

## 🛠️ Tech Stack
- **Backend**: Spring Boot (Web, Security, Data JPA)
- **Database**: Neon PostgreSQL (cloud-hosted)
- **Documentation**: SpringDoc OpenAPI Starter Webmvc UI
- **Build Tool**: Maven
- **Language**: Java 17+

---

## 🗄️ Database Setup
The app uses **Neon PostgreSQL (cloud)** as its primary database.

### Cloud Deployment
Database credentials are injected via environment variables in Render:

- `DB_URL` → Neon connection string  
  Example:  
  ```
  postgresql://USERNAME:PASSWORD@ep-calm-glitter-ateos50j-pooler.c-9.us-east-1.aws.neon.tech/fitness?sslmode=require&channel_binding=require
  ```
- `DB_USER` → Neon username  
- `DB_PASSWORD` → Neon password  

⚠️ **Security Note:** Do not hardcode credentials in `application.properties` or commit them to GitHub. Always use environment variables.

### application.properties
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
```


## 📑 API Usage (via Postman)

You can interact with the Fitness Tracking API using **Postman**.  

### 1️⃣ Import OpenAPI Spec
- Open Postman → Click **Import**.  
- Paste the OpenAPI JSON URL:  
  ```
  https://fitness-mono-1-136j.onrender.com/v3/api-docs
  ```
- Postman will automatically generate all endpoints with request/response schemas.

---

### 2️⃣ Register a User
- Method: `POST`  
- URL:  
  ```
  https://fitness-mono-1-136j.onrender.com/api/auth/register
  ```
- Body (JSON):  
  ```json
  {
    "username": "shrey",
    "password": "securePass"
  }
  ```
- Click **Send** → You’ll get a `201 Created` response with user details.

---

### 3️⃣ Login to Get JWT Token
- Method: `POST`  
- URL:  
  ```
  https://fitness-mono-1-136j.onrender.com/api/auth/login
  ```
- Body (JSON):  
  ```json
  {
    "username": "shrey",
    "password": "securePass"
  }
  ```
- Response will include a `token` field. Copy this JWT.

---

### 4️⃣ Add Activity
- Method: `POST`  
- URL:  
  ```
  https://fitness-mono-1-136j.onrender.com/api/activities
  ```
- Headers:  
  ```
  Authorization: Bearer <JWT_TOKEN>
  Content-Type: application/json
  ```
- Body (JSON):  
  ```json
  {
    "type": "Running",
    "duration": 30,
    "calories": 250
  }
  ```
- Click **Send** → You’ll get a `200 OK` response with activity details.

---

### 5️⃣ View Activities
- Method: `GET`  
- URL:  
  ```
  https://fitness-mono-1-136j.onrender.com/api/activities
  ```
- Headers:  
  ```
  Authorization: Bearer <JWT_TOKEN>
  ```
- Response: List of activities for the logged-in user.

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

## 🐳 Docker Setup

### Dockerfile
```dockerfile
# Stage 1: Build the JAR
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/fitness-tracking-api-*.jar app.jar

# Expose port
EXPOSE 8080

# Environment variables for Neon DB
ENV DB_URL=jdbc:postgresql://<neon-host>/<db-name>?sslmode=require&channel_binding=require
ENV DB_USER=<username>
ENV DB_PASSWORD=<password>

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
```

### Build & Run
```bash
docker build -t fitness-tracking-api .
docker run -p 8080:8080 \
  -e DB_URL=postgresql://USERNAME:PASSWORD@ep-calm-glitter-ateos50j-pooler.c-9.us-east-1.aws.neon.tech/fitness?sslmode=require&channel_binding=require \
  -e DB_USER=USERNAME \
  -e DB_PASSWORD=PASSWORD \
  fitness-tracking-api
```



