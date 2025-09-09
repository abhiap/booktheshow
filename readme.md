# Movie Booking System

A Spring Boot-based REST API system for browsing movie theatres and booking tickets with seat selection.

## Overview

This application provides two main functionalities:
1. **Browse Theatres**: Find theatres showing specific movies in a city on a given date
2. **Book Tickets**: Reserve movie tickets by selecting theatre, show timing, and preferred seats

## Prerequisites

- Java 21 or higher

## How to Run

### Using Gradle Wrapper (Recommended)

```bash
# Clone the repository
git clone <repository-url>
cd booktheshow

# Run the application
./gradlew bootRun
```

### Using Gradle (if installed globally)

```bash
gradle bootRun
```

### Using JAR

```bash
# Build the JAR
./gradlew build

# Run the JAR
java -jar build/libs/booktheshow-1.0.0.jar
```

The application will start on `http://localhost:8080`

## Database

The application uses H2 in-memory database with sample data automatically loaded on startup.

- **H2 Console**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`


## API Endpoints

### 1. Browse Theatres

Find theatres showing a specific movie in a city on a given date.

#### POST /api/v1/browse/theatres

**Request:**
```json
{
  "movieId": 1,
  "cityId": 1,
  "showDate": "2025-09-09"
}
```

**Response:**
```json
{
  "movieId": 1,
  "movieTitle": "Avengers: Endgame",
  "movieLanguage": "English",
  "cityId": 1,
  "cityName": "Mumbai",
  "showDate": "2025-09-09",
  "theatres": [
    {
      "theatreId": 1,
      "theatreName": "PVR Cinemas - Phoenix Mills",
      "address": "Phoenix Mills, Lower Parel, Mumbai",
      "totalScreens": 8,
      "showTimings": [
        {
          "showId": 1,
          "showTime": "10:00",
          "screenNumber": 1,
          "totalSeats": 200,
          "availableSeats": 150,
          "ticketPrice": 350.00
        },
        {
          "showId": 2,
          "showTime": "14:30",
          "screenNumber": 2,
          "totalSeats": 180,
          "availableSeats": 120,
          "ticketPrice": 400.00
        },
        {
          "showId": 3,
          "showTime": "19:00",
          "screenNumber": 1,
          "totalSeats": 200,
          "availableSeats": 180,
          "ticketPrice": 450.00
        }
      ]
    },
    {
      "theatreId": 2,
      "theatreName": "INOX - R City Mall",
      "address": "R City Mall, Ghatkopar, Mumbai",
      "totalScreens": 6,
      "showTimings": [
        {
          "showId": 4,
          "showTime": "11:30",
          "screenNumber": 1,
          "totalSeats": 150,
          "availableSeats": 100,
          "ticketPrice": 300.00
        },
        {
          "showId": 5,
          "showTime": "16:00",
          "screenNumber": 2,
          "totalSeats": 150,
          "availableSeats": 140,
          "ticketPrice": 350.00
        }
      ]
    }
  ]
}
```

#### GET /api/v1/browse/theatres

**Parameters:**
- `movieId` (required): Movie ID
- `cityId` (required): City ID
- `showDate` (required): Show date in YYYY-MM-DD format

**Example:**
```
GET /api/v1/browse/theatres?movieId=1&cityId=1&showDate=2025-09-09
```

**Response:** Same as POST request above.

### 2. Book Movie Tickets

Reserve movie tickets by selecting show, seats, and payment method.

#### POST /api/v1/bookings

**Request:**
```json
{
  "showId": 1,
  "seatNumbers": ["A1", "A2", "A3"],
  "userId": "user123"
}
```

**Response:**
```json
{
  "bookingId": 101,
  "showId": 1,
  "status": "CONFIRMED",
  "seats": ["A1", "A2", "A3"],
  "amount": 1050.00,
  "paymentStatus": "SUCCESS",
  "paymentReference": "PAY-550e8400-e29b-41d4-a716-446655440000"
}
```

## Error Responses

All endpoints return structured error responses for failures:

**404 Not Found (Resource not found):**
```json
{
  "errorCode": "MOVIE_NOT_FOUND",
  "message": "Movie not found with ID: 999"
}
```

**400 Bad Request (Business logic error):**

**400 Bad Request (Validation error):**

**500 Internal Server Error:**

## Testing

Run the test suite:

```bash
./gradlew test
```

Generate test report:
```bash
./gradlew test jacocoTestReport
```

Test report will be available at `build/reports/tests/test/index.html`

## Health Check

Check if the application is running:
```
GET /actuator/health
```

## Configuration

Key application properties can be configured in `src/main/resources/application.yml`:

- Server port: `server.port=8080`
- Database URL: `spring.datasource.url=jdbc:h2:mem:testdb`
- Logging level: `logging.level.org.aap.booking=DEBUG`