# Task Management Service

A simple Spring Boot REST API for managing tasks with JWT-based authentication.

## Tech stack
- Java 17, Spring Boot 3
- Spring Web, Spring Security (JWT)
- Spring Data JPA + H2 (in-memory)
- ModelMapper
- springdoc-openapi (Swagger UI)

## Prerequisites
- JDK 17+
- Maven 3.9+ (or use the included Maven wrapper ./mvnw)

## Quick start
1) Clone this repo and open a terminal in the project root.
2) Start the service:
   - Using Maven wrapper:
     - macOS/Linux: `./task-management-service/mvnw -f task-management-service spring-boot:run`
     - Windows: `task-management-service\mvnw.cmd -f task-management-service spring-boot:run`
   - Or with Maven installed: `mvn -f task-management-service spring-boot:run`
3) The app runs on http://localhost:9003

## Configuration
Default config is in `task-management-service/src/main/resources/application.properties`:
- Port: `server.port=9003`
- DB: H2 in-memory `jdbc:h2:mem:testdb`
- JWT: `microfinance.app.jwtSecret`, `microfinance.app.jwtExpirationMs`
- JPA: `spring.jpa.hibernate.ddl-auto=create-drop` (recreates schema on each start)

## Authentication flow
- Register: `POST /auth/register` (no auth)
- Login: `POST /auth/login` → returns JWT token
- Use the token in `Authorization: Bearer <JWT>` header for protected endpoints.

Roles: The app seeds `ROLE_USER` and `ROLE_ADMIN` on startup. New users are registered as `ROLE_USER`.

## Main endpoints (brief)
Base URL: `http://localhost:9003`

Auth
- POST `/auth/register` — create user
- POST `/auth/login` — get JWT

Tasks (require Authorization: Bearer <JWT> with role USER)
- POST `/api/tasks` — create task
- PUT `/api/tasks/{taskId}` — edit task
- DELETE `/api/tasks/{taskId}` — delete task
- GET `/api/tasks/{taskId}` — get task by ID
- GET `/api/tasks` — list tasks

## Example requests
Register
curl -X POST http://localhost:9003/auth/register \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "John",
        "lastName": "Doe",
        "username": "johndoe",
        "phoneNumber": "1234567890",
        "password": "Passw0rd!"
      }'

Login
curl -X POST http://localhost:9003/auth/login \
  -H "Content-Type: application/json" \
  -d '{ "username": "johndoe", "password": "Passw0rd!" }'

Create task
export TOKEN="<JWT_FROM_LOGIN>"
curl -X POST http://localhost:9003/api/tasks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
        "title": "My first task",
        "description": "Do something important"
      }'

Get task by ID
curl -X GET http://localhost:9003/api/tasks/1 \
  -H "Accept: application/json" \
  -H "Authorization: Bearer $TOKEN"

Delete task
curl -X DELETE http://localhost:9003/api/tasks/1 \
  -H "Authorization: Bearer $TOKEN" 

## Notes
- Logs are verbose for Spring Security by default; adjust in `application.properties` if needed.
