# Tennis Club Reservation System

This project is an application for managing reservations in a tennis club. It provides REST API endpoints for managing courts and reservations.

The application is built with Spring Boot and uses JPA with in-memory H2 database.

---

## API Endpoints

All API endpoints are prefixed with `/api`.

### Courts

Path: `/api/courts`

| Method   | Endpoint             | Description                                      | Request Body (JSON)        | Response         |
|:---------|:---------------------|:-------------------------------------------------|:---------------------------|:-----------------|
| `POST`   | `/api/courts`        | Creates a new court.                             | `{"surfaceType": "Clay"}`  | HTTP Status      |
| `GET`    | `/api/courts`        | Retrieves a list of all courts.                  | None                       | `List<CourtDto>` |
| `GET`    | `/api/courts/{id}`   | Retrieves a court by its ID.                     | None                       | `CourtDto`       |
| `PUT`    | `/api/courts/{id}`   | Updates the surface type of an existing court.   | `{"surfaceType": "Grass"}` | HTTP Status      |
| `DELETE` | `/api/courts/{id}`   | Deletes a court by its ID.                       | None                       | HTTP Status      |

---

### Reservations

Path: `/api/reservations`

| Method   | Endpoint                                                      | Description                                                                                    | Request Body (JSON)                                                                                                                                                                       | Response               |
|:---------|:--------------------------------------------------------------|:-----------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:-----------------------|
| `GET`    | `/api/reservations`                                           | Retrieves a list of all reservations.                                                          | None                                                                                                                                                                                      | `List<ReservationDto>` |
| `GET`    | `/api/reservations/by-court/{id}`                             | Retrieves reservations for a specific court, sorted by creation date.                          | None                                                                                                                                                                                      | `List<ReservationDto>` |
| `GET`    | `/api/reservations/by-phone/{phoneNumber}?futureOnly=false`   | Retrieves reservations for a specific phone number. Only future reservations can be requested. | None                                                                                                                                                                                      | `List<ReservationDto>` |                                                        |
| `PUT`    | `/api/reservations/{id}`                                      | Updates an existing reservation.                                                               | `{"courtId": 1,"customerName": "John","customerSurname": "Doe","customerPhoneNumber": "123456789","startTime": "02.06.2025 16:00","endTime": "02.06.2025 17:00","isDouble": true }`       | `Double`               |
| `DELETE` | `/api/reservations/{id}`                                      | Deletes a reservation by its ID.                                                               | None                                                                                                                                                                                      | HTTP Status            |
| `POST`   | `/api/reservations/new-reservation`                           | Creates a new reservation.                                                                     | `{"courtId": 1,"customerName": "John","customerSurname": "Doe","customerPhoneNumber": "123456789","startTime": "02.06.2025 16:00","endTime": "02.06.2025 17:00","isDouble": true }`       | `Double`               |
