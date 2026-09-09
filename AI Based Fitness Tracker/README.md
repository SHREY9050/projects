# AI Based Fitness Tracker

A standalone Spring Boot microservices application. The separate `fitness-microservice backend app` is a read-only functional reference, not a dependency.

## Architecture

- `config-server` — Spring Cloud Config Server, port 8888
- `discovery-server` — Eureka service discovery, port 8761
- `api-gateway` — JWT validation, CORS, and the only frontend API entry point, port 8080
- `user-service` — Keycloak user-profile projection in PostgreSQL, port 8081
- `activity-service` — MongoDB activity data and Kafka activity events, port 8082
- `recommendation-service` — MongoDB coaching, Kafka consumer, optional Gemini, port 8083
- `frontend` — Vite/React dashboard with Keycloak Authorization Code + PKCE, port 5173

## Security

Keycloak owns registration, login, logout, roles, and passwords. The frontend never receives a password. It sends a Keycloak token only to the Gateway. Gateway and services validate that JWT and derive activity ownership from its subject. Local profiles are non-sensitive; no password field is stored or returned.

## Run locally

Prerequisites: Java 17+, Maven 3.9+, Node 20+, Docker Desktop.

```powershell
cd "AI Based Fitness Tracker"
docker compose up -d
```

In separate terminals, start services in this order:

```powershell
mvn -pl config-server spring-boot:run
mvn -pl discovery-server spring-boot:run
mvn -pl user-service spring-boot:run
mvn -pl activity-service spring-boot:run
mvn -pl recommendation-service spring-boot:run
mvn -pl api-gateway spring-boot:run
```

Then run the frontend:

```powershell
cd frontend
Copy-Item .env.example .env
npm install
npm run dev
```

Open `http://localhost:5173`; register through Keycloak at `http://localhost:8180`. To use Gemini, set `GEMINI_API_KEY` when starting `recommendation-service`. Without it, the service uses a clearly labelled rule-based fallback.

## Build

```powershell
mvn test
cd frontend
npm run build
```

The dashboard is driven by actual activity data: activity type, duration, calories, steps, distance, heart rate, pace, weekly count/minutes/calories, workout streak, and progress toward a 150-minute weekly goal.

