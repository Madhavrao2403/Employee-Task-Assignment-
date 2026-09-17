# 👨‍💼 Employee Task Assignment API

A Spring Boot REST API for managing employees and assigning tasks to them. This project focuses on **JPA entity relationships**, foreign keys, relationship-based queries, and DTOs.

## 🚀 Features

* Create employees
* Create tasks
* Assign tasks to employees
* One Employee → Many Tasks relationship
* Many Tasks → One Employee relationship
* Retrieve all tasks assigned to a specific employee
* Use DTOs to avoid recursive JSON serialization
* Store employee-task relationships using MySQL foreign keys

## 🛠️ Technologies Used

* Java
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Lombok
* Maven
* REST API

## 📂 Project Structure

```text
src/main/java/com/mini/EmployeeTask
│
├── controller
│   ├── EmployeeController.java
│   └── TaskController.java
│
├── dto
│   ├── EmployeeRequest.java
│   ├── TaskRequest.java
│   └── TaskResponse.java
│
├── model
│   ├── Employee.java
│   └── Task.java
│
├── repository
│   ├── EmployeeRepository.java
│   └── TaskRepository.java
│
└── service
    ├── EmployeeService.java
    └── TaskService.java
```

## 🔗 JPA Relationship

The project uses a bidirectional relationship between `Employee` and `Task`.

### Employee

One employee can have multiple tasks:

```java
@OneToMany(mappedBy = "employee")
private List<Task> tasks;
```

### Task

Many tasks can belong to one employee:

```java
@ManyToOne
@JoinColumn(name = "employee_id")
private Employee employee;
```

The database relationship is:

```text
employees
    │
    │ 1
    │
    │
    │ *
tasks
```

The `tasks` table contains the foreign key:

```text
employee_id → employees.id
```

## 🧠 Key Concepts Learned

### 1. `@ManyToOne`

Used in the `Task` entity because multiple tasks can belong to one employee.

### 2. `@OneToMany`

Used in the `Employee` entity because one employee can have many tasks.

### 3. `@JoinColumn`

```java
@JoinColumn(name = "employee_id")
```

Defines the foreign-key column used to connect the two entities.

### 4. `mappedBy`

```java
@OneToMany(mappedBy = "employee")
```

The `employee` value refers to the `employee` field inside the `Task` entity.

It tells JPA that `Task.employee` owns the relationship.

### 5. Spring Data Derived Query

```java
List<Task> findByEmployeeId(Long id);
```

Spring Data JPA derives the query from the method name and searches tasks using the employee's ID.

### 6. Relationship DTOs

Instead of exposing the complete `Employee` object inside `TaskResponse`, the API returns:

```java
private Long employeeId;
```

This prevents recursive JSON serialization.

## 📡 API Endpoints

### Create Employee

```http
POST /employees
```

Request:

```json
{
    "name": "Ravi",
    "email": "ravi@gmail.com",
    "department": "IT"
}
```

Example response:

```json
{
    "id": 2,
    "name": "Ravi",
    "email": "ravi@gmail.com",
    "department": "IT",
    "tasks": []
}
```

### Create Task

```http
POST /tasks
```

Request:

```json
{
    "title": "Build Login API",
    "description": "Create login endpoint",
    "employeeId": 2
}
```

Example response:

```json
{
    "id": 1,
    "title": "Build Login API",
    "description": "Create login endpoint",
    "status": true,
    "employeeId": 2
}
```

### Get Employee Tasks

```http
GET /employees/{id}/tasks
```

Example:

```http
GET /employees/2/tasks
```

Example response:

```json
[
    {
        "id": 1,
        "title": "Build Login API",
        "description": "Create login endpoint",
        "status": true,
        "employeeId": 2
    },
    {
        "id": 4,
        "title": "Design Database",
        "description": "Create tables and relationships for the project",
        "status": true,
        "employeeId": 2
    }
]
```

## 🔄 Request Flow

### Creating a Task

```text
Client
  ↓
POST /tasks
  ↓
TaskRequest
  ↓
TaskController
  ↓
TaskService
  ↓
Find Employee by employeeId
  ↓
task.setEmployee(employee)
  ↓
TaskRepository
  ↓
MySQL
```

### Getting Employee Tasks

```text
Client
  ↓
GET /employees/{id}/tasks
  ↓
EmployeeController
  ↓
EmployeeService
  ↓
TaskRepository
  ↓
findByEmployeeId()
  ↓
Task → TaskResponse
  ↓
JSON Response
```

## 🐛 Problem Solved: Recursive JSON

Initially, returning the entities directly caused recursive serialization:

```text
Task
 ↓
Employee
 ↓
Tasks
 ↓
Task
 ↓
Employee
 ↓
Tasks
 ↓
...
```

The problem was solved by introducing `TaskResponse`:

```java
private Long employeeId;
```

instead of exposing:

```java
private Employee employee;
```

This keeps the API response simple and prevents infinite recursion.

## 🗄️ Database Relationship

Conceptually, the database contains:

```text
employees
--------------------------------
id | name | email | department
--------------------------------
2  | Ravi | ...   | IT
```

```text
tasks
----------------------------------------------------
id | title | description | status | employee_id
----------------------------------------------------
1  | Login | Login API   | true   | 2
4  | DB    | Database    | true   | 2
```

Therefore:

```text
Employee 2
   │
   ├── Task 1
   └── Task 4
```

## ▶️ How to Run

### 1. Clone the repository

```bash
git clone <your-repository-url>
```

### 2. Configure MySQL

Create a database:

```sql
CREATE DATABASE employee_task;
```

Configure your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_task
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

## 📚 What I Learned

Through this project, I learned how to:

* Model relationships between JPA entities
* Use `@OneToMany`
* Use `@ManyToOne`
* Use `@JoinColumn`
* Understand foreign keys
* Understand the owning side of a JPA relationship
* Use `mappedBy`
* Create relationship-based Spring Data queries
* Pass relationship IDs through DTOs
* Convert entities into response DTOs
* Identify and solve recursive JSON serialization
* Connect related entities through a Spring Boot service layer

## 🏷️ Challenge

**Day 7/50 — Java + Spring Boot Mini Project Challenge**
