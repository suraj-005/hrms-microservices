# HRMS Microservices Project

A production-style Human Resource Management System built using:

## Tech Stack
- Java 17
- Spring Boot
- Spring Cloud (Eureka, Gateway)
- Spring Security + JWT
- Kafka (Event-driven communication)
- PostgreSQL
- Docker & Docker Compose

## Microservices
- API Gateway
- Auth Service (Access + Refresh Token)
- Employee Service
- Attendance Service
- Leave Management Service

## Architecture
- Microservices architecture
- Event-driven communication using Kafka
- Stateless authentication with JWT
- Service discovery using Eureka

## How to run
1. Start Kafka using Docker Compose
2. Start Eureka Server
3. Start all microservices
4. Access APIs via API Gateway
