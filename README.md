# Job Application Tracker API

A REST API for tracking job applications with JWT authentication and user ownership.

## Tech Stack
- Java 17
- Spring Boot
- Spring Security (JWT)
- PostgreSQL
- Flyway
- Swagger/OpenAPI
- Docker

## Features
- User registration and login with JWT
- Create, read, update, delete job applications
- Filter applications by status
- Application summary dashboard
- User ownership — users can only access their own data

## Running Locally

### Prerequisites
- Docker Desktop
- Java 17

### Steps
1. Clone the repository
2. Create `.env` file based on `.env.example`
3. Run `./mvnw clean package -DskipTests`
4. Run `docker-compose up`
5. Access API at `http://localhost:8080`
6. Swagger UI at `http://localhost:8080/swagger-ui/index.html`

## API Endpoints

### Auth
- POST /auth/register
- POST /auth/login

### Applications (JWT Required)
- POST /applications
- GET /applications
- GET /applications/{id}
- PUT /applications/{id}
- DELETE /applications/{id}
- GET /applications/filter?status=
- GET /applications/summary