# Learning Management System (LMS)

A professional web-based Learning Management System built using Java, Spring Boot, Spring MVC, Spring Data JPA, MySQL, and Thymeleaf. The application helps manage students, courses, enrollments, and reports through a clean admin dashboard.

## Features

- Secure registration and login using Spring Security and BCrypt password hashing
- Role-based session protection for all LMS pages
- Dashboard with summary cards and charts
- Student management with add, search, edit, view, and delete
- Course management with add, search, edit, view, and delete
- Enrollment management with dropdown-based student and course selection
- Reports page with summary and date filters
- MySQL database design with normalized relationships
- Responsive SaaS-style UI built with Bootstrap 5 and Thymeleaf

## Technology Stack

- Java 17+
- Spring Boot 3.3.x
- Spring MVC
- Spring Data JPA
- Spring Security
- MySQL
- Thymeleaf
- HTML5 / CSS3 / JavaScript
- Bootstrap 5
- Maven

## System Architecture

The application follows a layered architecture:

- Controller layer for handling HTTP requests
- Service layer for business logic
- Repository layer for database access
- Model layer for JPA entities
- Config layer for security and application setup

## Database Structure

The project uses MySQL database named `lms_db` and includes tables for:

- `users`
- `students`
- `courses`
- `enrollments`

Foreign keys and JPA relationships are used to keep data normalized.

## Project Folder Structure

```text
LMS-Project/
├── src/
│   ├── main/
│   │   ├── java/com/lms/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── LmsApplication.java
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   ├── templates/
│   │   │   ├── application.properties
│   │   │   ├── application-dev.properties
│   │   │   └── sql/
│   └── test/
├── pom.xml
├── README.md
└── .gitignore
```

## Installation Instructions

### 1. Install Java and Maven

- Install Java 17 or newer
- Install Maven
- Confirm with:

```bash
java -version
mvn -version
```

### 2. Create MySQL Database

Open MySQL and run:

```sql
CREATE DATABASE lms_db;
```

### 3. Configure Database Credentials

Edit `src/main/resources/application.properties` and set your MySQL username and password:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lms_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 4. Run the Application

From the project root:

```bash
mvn spring-boot:run
```

The app normally runs at:

```text
http://localhost:8080
```

For local development without MySQL installed, use the dev profile:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## Application URLs

- Login: `http://localhost:8080/login`
- Registration: `http://localhost:8080/register`
- Dashboard: `http://localhost:8080/dashboard`

## Sample Data

Sample SQL scripts are available in `src/main/resources/sql/` for testing the student, course, and enrollment workflows.

## Screenshots

Add screenshots here before presenting the project:

- Login page
- Dashboard
- Students module
- Courses module
- Enrollments module
- Reports page

## Future Improvements

- Add admin role and permissions
- Add course materials and lectures
- Add assignment and grading modules
- Add notifications and email reminders
- Add PDF report export

## Running in VS Code

1. Open the project in VS Code
2. Open the terminal
3. Run:

```bash
mvn spring-boot:run
```
4. Open the browser to:

```text
http://localhost:8080
```

## Notes

This project is designed for a B.Tech CSE college project and can be used for resume, portfolio, and interview demonstrations.
