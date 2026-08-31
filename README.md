# Patient Appointment Scheduler API

REST API for managing patients, doctors, time slots and medical appointments.

## Tech Stack

* Java
* Spring Boot
* Spring Data JPA / Hibernate
* PostgreSQL
* Bean Validation
* Lombok

## Features

* CRUD operations for patients and doctors
* Doctor specialties
* Doctor time slots
* Appointment booking and cancellation
* Available slot search by specialty and date
* Appointment conflict detection
* Optimistic locking for concurrent slot booking
* DTOs and entity mappers
* Global exception handling with `@ControllerAdvice`
* RESTful API design

## Architecture

The project follows a layered architecture:

`Controller → Service → Repository → Database`

Entities are not exposed directly through the API. Request and response DTOs are used instead.

## Main Entities

* `User`
* `DoctorProfile`
* `Specialty`
* `TimeSlot`
* `Appointment`

## Status

JWT authentication and role-based authorization are planned for the next stage.
