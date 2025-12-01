# ENDPOINTS.md

This project uses **Swagger UI** for interactive API documentation.

After starting the server, visit:
**http://localhost:8080/swagger-ui/index.html**

---

## Core Endpoints Overview

| Method | Endpoint | Description |
| ------- | -------- | ----------- |
| POST | /api/students/submissions | Submit homework |
| GET | /api/students/submissions | View submissions (filter by grade/assignment) |
| GET | /api/students/submissions/{id}/download | Download homework |
| GET | /api/teachers/submissions | List all submissions (filter by student/date/assignment) |
| PATCH | /api/teachers/submissions/{id}/grade | Grade submission |

---

## Setup Endpoints
| Method | Endpoint | Description |
| ------- | -------- | ----------- |
| POST | /api/students | Create new student |
| POST | /api/teachers | Create new teacher |
| POST | /api/assignments | Create new assignment |

All endpoints are detailed and testable via **Swagger UI**.

---

## Base URL

<http://localhost:8080/api>

## Student Endpoints

/api/students

### 1\. Submit Homework

**POST** /api/students/submissions

**Description**

Uploads a homework file for a specific assignment.

**Request (Multipart Form Data)**

| **Field** | **Type** | **Required** | **Description** |
| --- | --- | --- | --- |
| studentId | number | Yes | ID of the student submitting |
| assignmentId | number | Yes | ID of the assignment |
| file | file | Yes | Homework file (.pdf or .jpeg) |

**Example (FormData)**

studentId=1

assignmentId=2

file=homework1.pdf

**Response (200)**
```json
{
    "id": 7,

    "filePath": "uploads\\1761138183751_Homework.pdf",

    "submittedAt": "2025-10-22T15:03:03.770963",

    "gradedAt": null,

    "grade": null,

    "teacherNotes": null
}
```
**Error Responses**

| **Code** | **Message** |
| --- | --- |
| 400 | "Only PDF or JPEG files are allowed" |
| 400 | "File too large. Maximum allowed size is 5MB" |
| 404 | "Student not found with id: X" |

---
### 2\. View Student Submissions

**GET** /api/students/submissions

**Query Parameters**

| **Param** | **Required** | **Type** | **Description** |
| --- | --- | --- | --- |
| studentId | Yes   | number | ID of student |
| assignmentName | No   | string | Filter by assignment name |
| grade | No   | string | Filter by grade (e.g., A, B, C) |

**Example Request**

GET /api/students/submissions?studentId=1&grade=A

**Example Response**
```json

{
    "assignmentName": "Math Homework 1",

    "studentName": "Alice Smith",

    "submittedAt": "2025-10-18T16:29:31.199807",
    
    "grade": "A",

    "teacherNotes": "Excellent improvement!"
}


```
---
### 3\. Download a Submission

**GET** /api/students/submissions/{id}/download

**Description**

Downloads a specific submission file.

**Example Request**

GET /api/students/submissions/7/download

**Response**

Downloads the file with appropriate headers:
```json
Content-Disposition: attachment; filename="169798333_homework1.pdf"
```
---
## Teacher Endpoints

/api/teachers

### 4\. View All Submissions (with filters)

**GET** /api/teachers/submissions

**Query Parameters**

| **Param** | **Required** | **Type** | **Description** |
| --- | --- | --- | --- |
| teacherId | Yes | number | ID of teacher |
| assignmentName | No  | string | Filter by assignment name |
| studentName | No  | string | Filter by student name |
| fromDate | No  | ISO datetime | Filter by start date |
| toDate | No  | ISO datetime | Filter by end date |

**Example Request**

GET /api/teachers/submissions?teacherId=5&assignmentName=Math Homework 1

**Example Response**
```json
\[

    {
        "assignmentName": "Math Homework 1",

        "studentName": "Alice Smith",

        "submittedAt": "2025-10-18T16:29:31.199807",

        "grade": "A",

        "teacherNotes": "Excellent improvement!"
    }

\]
```
---
### 5\. Grade a Submission

**PATCH** /api/teachers/submissions/{id}/grade

**Request (JSON)**
```json
{

"grade": "A",

"teacherNotes": "Excellent improvement!"

}
```
**Example Request**

PATCH /api/teachers/submissions/4/grade

**Response (200)**
```json
{
    "assignmentName": "Math Homework 1",

    "studentName": "Alice Smith",

    "submittedAt": "2025-10-20T11:53:34.287015",
    
    "grade": "A",
    
    "teacherNotes": "Excellent improvement!"
}
```
---
## Setup Endpoints

### 6\. Create Student

**POST** /api/students

**Description**

Registers a new student in the system.

**Request (JSON)**
```json
{

"name": "Alice Johnson",

"email": "alice@example.com"

}
```

**Response (200)**
```json
{

"id": 1,

"name": "Alice Johnson",

"email": "alice@example.com",

"submissions": []

}
```
---
### 7\. Create Teacher

**POST** /api/teachers

**Description**

Registers a new teacher in the system.

**Request (JSON)**
```json
{

"name": "Mr. Smith",

"email": "smith@example.com"

}
```
**Response (200)**
```json
{
    "id": 2,

    "name": "Mr. Smith",

    "email": "smith@example.com"
}
```
---
### 8\. Create Assignment

**POST** /api/assignments

**Description**

Creates a new assignment linked to a teacher.

**Request (JSON)**
```json
{

"teacherId": 2,

"title": "Math Homework 1",

"description": "Complete exercises 1-10 from chapter 4."

}
```
**Response (200)**
```json
{
    "id": 2,

    "name": "Math Homework 1",

    "description": "Complete exercises 1-10 from chapter 4.",

    "createdAt": "2025-10-22T14:55:50.8235416"
}
```
**Error Responses**

| **Code** | **Message** |
| --- | --- |
| 400 | "Teacher not found" |
| 400 | "Missing required fields" |

---