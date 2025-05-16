# Logging Microservice

This is a microservice for centralized logging in the Todo application. It provides REST APIs for creating and retrieving logs of various types.

## Features

- Store logs with different levels (TRACE, DEBUG, INFO, WARN, ERROR, FATAL)
- Multiple log types (CHAIN_LOG, FILTER_LOG, USER_ACTION, SYSTEM_EVENT, API_CALL, etc.)
- Filtering and pagination
- Chain logs and Filter logs support
- Log maintenance and cleanup
- Statistics on log distribution

## Technologies

- Java 17
- Spring Boot
- MongoDB
- Docker

## Getting Started

### Prerequisites

- Docker and Docker Compose
- Java 17 (for local development)
- Maven (for local development)

### Running with Docker Compose

1. Clone the repository
2. Navigate to the project directory
3. Run Docker Compose:

```bash
docker-compose up -d
```

This will:

- Build the Logging service using the Dockerfile
- Start MongoDB
- Configure all necessary connections

### API Endpoints

#### Main Log Endpoints

- `POST /api/logs` - Create a new log
- `GET /api/logs` - Get all logs (paginated)
- `GET /api/logs/{id}` - Get a log by ID
- `GET /api/logs/level/{level}` - Get logs by level
- `GET /api/logs/type/{type}` - Get logs by type
- `GET /api/logs/user/{userId}` - Get logs by user
- `POST /api/logs/search` - Search logs with complex filter

#### Chain Log Endpoints

- `POST /api/logs/chain` - Create a chain log
- `GET /api/logs/chain` - Get all chain logs
- `GET /api/logs/chain/user/{userId}` - Get chain logs by user

#### Filter Log Endpoints

- `POST /api/logs/filter` - Create a filter log
- `GET /api/logs/filter` - Get all filter logs
- `GET /api/logs/filter/user/{userId}` - Get filter logs by user
- `GET /api/logs/filter/criteria` - Get filter logs by criteria

#### Maintenance Endpoints

- `DELETE /api/logs/maintenance/cleanup/{days}` - Delete logs older than specified days
- `GET /api/logs/maintenance/stats/source` - Get statistics by source
- `GET /api/logs/maintenance/stats/type` - Get statistics by type
- `GET /api/logs/maintenance/stats/level` - Get statistics by level

## Architecture

This microservice follows a layered architecture:

1. **Controller Layer**: REST API endpoints
2. **Service Layer**: Business logic
3. **Repository Layer**: Data access
4. **Model Layer**: Domain models

It implements the Factory pattern for log creation and uses MongoDB for persistent storage.
