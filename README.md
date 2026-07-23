# 🚀 Projects Repository

This repository contains multiple projects developed by **Shrey**.  
Each project has its own folder and dedicated README with setup instructions.

---

## 📂 Projects

### 1. Fitness Monolith App
- **Type**: Spring Boot monolithic backend
- **Version**: v1.0
- **Database**: Neon PostgreSQL (cloud-hosted)
- **Deployment**: Render
- **Docs**: OpenAPI 3.1 (Swagger UI)
- **Live URL**: [Swagger UI](https://fitness-mono-1-136j.onrender.com/swagger-ui/index.html)

👉 Detailed instructions: [Fitness Monolith App README](./Fitness%20Monolith%20App/README.md)

---

### 2. Smart Email AI Assistant
- **Type**: AI-powered email assistant
- **Tech Stack**: Python/ML + integrations
- **Features**: Automates email drafting, smart replies, and productivity workflows

👉 Detailed instructions: [Smart Email AI Assistant README](./Smart%20Email%20AI%20Assistant/README.md)

---

### 3. Fitness Microservices Backend App
- **Type**: Spring Boot microservices architecture
- **Modules**:  
  - `activityservice` → Manages fitness activities (CRUD, validation, MongoDB storage)  
  - `aiservice` → AI-powered recommendations using Gemini + message listeners  
  - `userservice` → User management (registration, roles, persistence)  
  - `gateway` → API Gateway with Keycloak integration, security filters, and user sync  
  - `configserver` → Centralized configuration management (Spring Cloud Config)  
  - `eureka` → Service discovery (Netflix Eureka)  
- **Tech Stack**: Spring Boot, Spring Cloud (Config, Eureka, Gateway), MongoDB, Keycloak, Gemini AI  
- **Features**:  
  - Distributed microservices with service discovery  
  - Centralized configuration via Config Server  
  - Secure authentication/authorization with Keycloak  
  - AI-driven activity recommendations  

👉 Detailed instructions: [Fitness Microservices Backend App README](./fitness-microservice-backend-app/README.md)

---

## 🛠️ How to Use
- Clone the repo:
  ```bash
  git clone https://github.com/SHREY9050/projects.git
