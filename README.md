# 🚀 Resume System Backend – Zidio Trial Task

This project is a **Spring Boot-based backend** for the **Zidio Resume System**, built as part of the trial task for the **Zidio Development Internship**.  
It provides secure APIs for **authentication**, **resume generation**, and **data management** — forming the backbone of a dynamic, real-time resume ecosystem.

---

## 🌐 Project Overview

This backend powers a **next-generation resume builder**, where users can:
- Register and authenticate securely using JWT tokens.
- Generate resumes dynamically.
- Retrieve previous resume versions and histories.
- Integrate with external internship, project, and skill modules.

---

## ⚙️ Tech Stack

| Component | Technology Used |
|------------|-----------------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| ORM | Spring Data JPA |
| Database | MySQL |
| Security | Spring Security + JWT |
| Build Tool | Maven |
| IDE | Eclipse |
| Version Control | Git & GitHub |
| Testing | Postman |

---

## 🧩 Core Backend Modules

### 1. **Entity Layer**
- `User.java` → Represents system users (with authentication credentials).
- `Resume.java` → Defines resume structure (id, userId, title, version, summary, contentJson, skills).

### 2. **DTO Layer**
- `ResumeDTO.java` → Transfers only required resume data to the frontend layer.
- `AuthRequest.java` / `AuthResponse.java` → Handles login and token responses.

### 3. **Repository Layer**
- `UserRepository.java` → For user authentication and data management.
- `ResumeRepository.java` → Provides CRUD and custom queries for resume data.

### 4. **Service Layer**
- `UserService.java` → Handles user registration and login logic.
- `ResumeService.java` → Business logic for creating, updating, fetching, and versioning resumes.
- Includes **auto version increment** and **summary auto-generation** based on content.

### 5. **Controller Layer**
- `AuthController.java` → Manages authentication (register, login, token issue).
- `ResumeController.java` → Handles all resume-related endpoints (CRUD + history).

### 6. **Security Layer**
- JWT-based Authentication filter.
- Custom `UserDetailsService` for verifying user credentials.
- Protects `/api/resumes/**` endpoints from unauthorized access.

### 7. **Configuration Layer**
- `application.properties` → Configures MySQL, Hibernate, and JPA properties.
- Supports environment-based configuration.

---



## 🧩 Core Modules & Folder Structure

src/main/java/com/zidio/resume_backend/
│
├── config/                     # Spring Security & JWT setup
│   ├── JwtFilter.java
│   ├── RestAuthenticationEntryPoint.java
│   └── SecurityConfig.java
│
├── controller/                 # REST Controllers
│   ├── AuthController.java     # Login & Register APIs
│   └── ResumeController.java   # Resume CRUD & generation
│
├── dto/                        # Data Transfer Objects
│   ├── AuthRequest.java
│   ├── AuthResponse.java
│   ├── ResumeDTO.java
│   └── SkillDTO.java
│
├── entity/                     # JPA Entities (Database Tables)
│   ├── User.java
│   ├── Resume.java
│   ├── Skill.java
│   └── Project.java
│
├── exception/                  # Custom Exceptions
│   └── InvalidCredentialsException.java
│
├── repository/                 # Spring Data JPA Repositories
│   ├── UserRepository.java
│   ├── ResumeRepository.java
│   └── ProjectRepository.java
│
├── service/                    # Business Logic
│   ├── AuthService.java
│   ├── ResumeService.java
│   └── CustomUserDetailsService.java
│
├── util/                       # Utility Classes
│   ├── JwtUtil.java            # JWT token generation & validation
│   └── ResumeGenerator.java    # Logic to generate resumes dynamically
│
└── ResumeBackendApplication.java  # Main Spring Boot entry point
---


## 🔐 Authentication Flow

1. **Register User**


POST /api/auth/register
Content-Type: application/json
{
{
  "fullName": "Atul Sharma ji",
  "email": "atulsharmagbn@gmail.com",
  "password": "Atul@12345",
  "role": "USER"
}


✅ Response:
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdHVsc2hhcm1hZ2JuQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzYxMzgyODEyLCJleHAiOjE3NjE0NjkyMTJ9.t_ew_c47P8zZrtlG5Vdez2VJDEanglefFvTn3AGb2i_Si4m7eq-tMOs-2dCx0G9hQX3IUqybCs_J86Plnhw8Uw",
    "role": "USER"
}


Login

POST /api/auth/login
Content-Type: application/json
{
  "email": "atulsharmagbn@gmail.com",
  "password": "Atul@12345",
}


✅ Response:

{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhdHVsc2hhcm1hZ2JuQGdtYWlsLmNvbSIsInJvbGUiOiJVU0VSIiwiaWF0IjoxNzYxMzgzMDU0LCJleHAiOjE3NjE0Njk0NTR9.geyYLHvFFpuAd77coowqTGWkp4CRS6CU_vD5SZezjsznJK8adIwjqiTpHYzDXZWyN30cjYmXUEr0JP759liujw",
    "role": "USER"
}


Use the token in all protected APIs:

Authorization: Bearer <JWT_TOKEN>

📄 Resume Management APIs
➤ Generate Resume
POST /api/resumes/generate?userId=1
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json


Request Body:

{
  "name": "John Doe",
  "email": "john@example.com",
  "education": "B.Tech in CS",
  "experience": "2 years at XYZ",
  "skills": ["Java", "Spring Boot", "SQL"]
}


✅ Response:

{
  "id": 1,
  "userId": 1,
  "title": "Resume for user 1",
  "version": 1,
  "contentJson": "{...}",
  "summary": "Auto-generated resume",
  "skills": ["Java", "Spring Boot"]
}

➤ Get Resume History
GET /api/resumes/history/{userId}
Authorization: Bearer <JWT_TOKEN>

➤ Get Resume by ID
GET /api/resumes/{id}
Authorization: Bearer <JWT_TOKEN>

➤ Get All Resumes
GET /api/resumes
Authorization: Bearer <JWT_TOKEN>

🧠 Unique & Core Highlights

✅ Modular Architecture — Clean separation of controller, service, repository layers
✅ JWT Authentication — Secure login/register & token-based access
✅ Resume Auto-Generation Logic — Generates sample resumes dynamically
✅ Database Integration (MySQL) — Persistent storage for users, resumes, skills, and projects
✅ RESTful APIs — Clean endpoints for frontend and third-party integrations
✅ Scalable Design — Ready for linking with internship, course, and hackathon modules
✅ Lombok & Spring Boot 3 — Modern annotations reduce boilerplate
✅ Integration Ready — Can easily connect with React, Angular, or mobile apps

🧪 Example Postman Setup

Add the following header in Postman:

Authorization: Bearer <JWT_TOKEN>


Then send a POST request to:

http://localhost:8080/api/resumes/generate?userId=1


with the sample JSON shown above.


1️⃣ Clone the Repository
git clone https://github.com/atulsharma1906/resume_system_backend.git
cd resume_system_backend

2️⃣ Configure Database in application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/resume_db
spring.datasource.username=root
spring.datasource.password=atul1234
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false

3️⃣ Run the Application
mvn spring-boot:run

4️⃣ Test APIs via Postman

Use http://localhost:8080 as the base URL.

🧾 Author

👨‍💻 Atul Sharma
📧 Email: atulsharmagbn@gmail.com

🌍 GitHub: atulsharma1906
