#  📚 Homework Submission Platform API
![Java](https://img.shields.io/badge/Java-17-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-API-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue)
![Swagger](https://img.shields.io/badge/Docs-Swagger-yellow)
![JUnit](https://img.shields.io/badge/Testing-JUnit-important)

A personal backend development project designed to strengthen my skills in building real-world REST APIs using Java and Spring Boot. The system enables students to submit homework (with file uploads) and allows teachers to review, filter, and grade submissions. It follows clean architecture principles with a service layer, DTOs, file handling, and unit-tested business logic.


## 🚀 Features
### Student Features

* Submit homework with file uploads (PDF/JPEG)

* View all their submissions

* Filter by:

    * Assignment name

    * Grade (A–F, ungraded)

### Teacher Features

* View all homework submissions

* Filter by:

    * Student name

    * Assignment name

    * Submission date range

    * Grade submissions (A–F + notes)

### System Features

* RESTful API design

* File upload support (MultipartFile)

* Local storage for uploaded files

* DTO-based request validation

* Swagger auto-generated documentation

* Unit-tested business logic using JUnit + Mockito

## 🧰 Tech Stack



* Backend: Java 17, Spring Boot
* Libraries/Frameworks: Spring Web, Spring Data JPA, Spring Validation
* Database: MySQL
* Testing: JUnit 5, Mockito
* Documentation: Swagger 
* Storage: Local filesystem (/uploads)
* Build Tool: Maven


## 📁 Project Structure
src/main/java/
 
 └── controller/         # REST controllers

 └── service/            # Business logic
 
 └── repository/         # JPA repositories
 
 └── model/              # JPA entities
 
 └── dto/                # Request/response DTOs
 
 └── exception/          # Custom exceptions
 
 └── config/             # Swagger configuration

## 🛠️ Getting Started
### 1. Clone the Repository
```json
git clone https://github.com/TMinnie/java_homework_platform_api.git

cd homework-platform
```
### 2. Configure Database

Update application.properties:
```json
spring.datasource.url=jdbc:mysql://localhost:3306/homework_platform
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the Application
```json
mvn spring-boot:run
```
### 4. Access API Documentation

Once running, visit:
➡️ http://localhost:8080/swagger-ui.html


## 🧪 Testing

Run unit tests:

```json
mvn test
```


Tests include:

* Service layer grading logic

* Filtering logic

* Error handling

## 📦 File Uploads

Files are stored in:
```json
/uploads/<studentName>/<filename>
```

Supported types: PDF, JPEG <br>Max size: 5MB

## 📌 Status

✅ Fully functional<br>
✅ Tested<br>
✅ Documented

🚀 Ready to extend with authentication or frontend UI
