# Library Management System

![Java](https://img.shields.io/badge/Java-17-blue)
![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)
![MVC](https://img.shields.io/badge/Architecture-MVC-brightgreen)

A complete web-based Library Management System with admin and student modules, built using Java/J2EE with MVC architecture.

## Features

### Admin Module
- ✅ Secure registration with email validation
- ✅ Book management (CRUD operations)
  - Add/Edit books with cover images
  - View all books with filtering
- ✅ Issue/return tracking
  - View issued books with student details
- ✅ Automated membership generation
  - Sends credentials via email

### Student Module
- ✅ Self-registration system
- ✅ Book search functionality
  - By title/author
- ✅ Book request system
  - Real-time availability check
- ✅ Renewal/return processing
- ✅ Personalized dashboard
  - Shows current loans/overdues

## Technology Stack

| Component        | Technology Used |
|------------------|-----------------|
| Frontend         | JSP, Bootstrap  |
| Backend          | Java Servlets   |
| Database         | MySQL           |
| Architecture     | MVC Pattern     |
| Email Service    | JavaMail API    |
| Validation       | Custom utilities|
| Security         | Role-based auth |

## System Requirements

- Java JDK 17+
- Apache Tomcat 9+
- MySQL 8.0+
- Gmail SMTP (for email services)


