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
👉 Detailed instructions: [Fitness Microservices Backend App README](./fitness-microservice%20backend%20app/README.md)
---
### 4. AI Phishing Message Checker
- **Type**: Flask web app with AI-powered risk analysis
- **Tech Stack**: Python, Flask, Google Gemini API
- **Features**:
  - Analyzes pasted messages (emails, SMS, chats) for phishing risk
  - Returns structured risk level (High/Medium/Low), reasoning, and recommended action
  - Retry/backoff handling for API rate limits
  - Clean, color-coded risk report UI
👉 Detailed instructions: [Phishing Message Checker README](./Phishing%20Message%20Checker/README.md)
---
## 🛠️ How to Use
- Clone the repo:
```bash
  git clone https://github.com/SHREY9050/projects.git
```

**One thing to check:** the link path `./Phishing%20Message%20Checker/README.md` assumes your folder is named exactly `Phishing Message Checker` (with spaces, hence `%20`). Match this to whatever you actually named the folder when you moved the files — if you went with `phishing-checker` (no spaces) instead, use `./phishing-checker/README.md` in the link.
