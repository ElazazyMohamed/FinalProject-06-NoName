FROM maven:3.8.4-openjdk-17 AS builder

WORKDIR /app

# Copy the pom.xml file
COPY pom.xml .

# Download all required dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Runtime stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=docker
ENV SPRING_DATA_MONGODB_HOST=mongodb
ENV SPRING_DATA_MONGODB_PORT=27017
ENV SPRING_DATA_MONGODB_DATABASE=logs
ENV SPRING_DATA_MONGODB_AUTHENTICATION_DATABASE=admin
ENV SPRING_DATA_MONGODB_USERNAME=mongodb
ENV SPRING_DATA_MONGODB_PASSWORD=mongodb

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"] 