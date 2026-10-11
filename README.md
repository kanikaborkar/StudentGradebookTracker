# Student Gradebook & Attendance Tracker

A layered Java application using JDBC and MySQL to manage student records, log bulk class attendance, calculate cumulative GPAs via database Stored Procedures, and generate student report cards.

---

##  Architecture & Features

This project implements a 4-layered **Data Access Object (DAO)** pattern:

* **Model Layer (POJOs):** `Student` and `AttendanceRecord` encapsulate database entity rows into Java objects.
* **DAO Layer:** Encapsulates all persistence logic away from the CLI controller.
    * **Batching (`AttendanceDAO`):** Uses `PreparedStatement` batch execution (`addBatch()` / `executeBatch()`) to perform bulk updates in a single database round-trip.
    * **Stored Procedures (`StudentDAO`):** Uses `CallableStatement` to invoke `CalculateStudentGPA` inside MySQL.
    * **Relational Joins (`GradebookDAO`):** Performs multi-table inner joins across `students`, `enrollments`, and `courses`.
* **Database Layer:** MySQL database schema enforced with Foreign Key constraints (`ON DELETE CASCADE`) and composite unique indexes.

---

##  Tech Stack

* **Language:** Java 17+
* **Database:** MySQL 8.0+
* **Connectivity:** JDBC (`mysql-connector-j`)
* **Build System:** Maven
* **IDE:** IntelliJ IDEA

---

##  Database Setup

Execute the following script in MySQL Workbench to initialize the schema:

```sql
CREATE DATABASE IF NOT EXISTS gradebook_db;
USE gradebook_db;

CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE courses (
    course_id INT AUTO_INCREMENT PRIMARY KEY,
    course_code VARCHAR(100) UNIQUE NOT NULL,
    course_name VARCHAR(50) NOT NULL,
    credits INT NOT NULL
);

CREATE TABLE enrollments (
    enrollment_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    grade DECIMAL(4, 2) DEFAULT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    UNIQUE(student_id, course_id)
);

CREATE TABLE attendance (
    attendance_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT NOT NULL,
    course_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status ENUM('PRESENT', 'ABSENT', 'LATE') NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
    UNIQUE(student_id, course_id, attendance_date)
);

How to Run
Clone this repository.
Open the project in IntelliJ IDEA as a Maven project.
Update database credentials in src/main/java/in/Framework/config/DatabaseConfig.java.
Run Main.java to start the interactive CLI application