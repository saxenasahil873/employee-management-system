# Employee Management System

A full-stack employee directory built with Java, Spring Boot, Spring Data JPA, MySQL/H2, and a lightweight HTML/CSS/JavaScript frontend.

## Features

- Employee CRUD with validation and duplicate-email protection
- Normalized `employees`, `departments`, and `roles` tables with foreign keys
- REST endpoints for employees, departments, and roles
- Centralized JSON error responses
- Searchable responsive frontend served by Spring Boot
- Demo data for a quick local start

## Run locally

Requires Java 17+ and Maven 3.9+.

```bash
mvn spring-boot:run
```

Open http://localhost:8080. The default `dev` profile uses an in-memory H2 database, so no database setup is required.

## Run with MySQL

Create a database and start the app with the `mysql` profile:

```bash
mysql -u root -p < src/main/resources/schema-mysql.sql
DB_USERNAME=root DB_PASSWORD=your-password mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

You can also set `DB_URL` for a different MySQL host/database. The API is available at `/api/employees`, `/api/departments`, and `/api/roles`.
