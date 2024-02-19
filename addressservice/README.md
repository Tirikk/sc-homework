# Address Service

This microservice provides an endpoint for getting addresses.

## Prerequisites

To build and run this application, you need:
- Java 17

## Build and Run

To build the application and run the included tests, use the following command:
```bash
./mvnw clean install
```
To start the application, use the following command:
```bash
./mvnw spring-boot:run
```

## Swagger UI

Swagger UI is available by opening the following URL in a browser:
[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

## Authentication

The provided endpoint is protected with basic authentication. Use the following dummy credentials:
```
user:password
```