FROM openjdk:23-ea-4-jdk-slim

WORKDIR /app

# Copy the jar file
COPY target/*.jar app.jar

# Environment variables for service connections
# These can be overridden when running the container
ENV SPRING_RABBITMQ_HOST=rabbitmq
ENV SPRING_RABBITMQ_PORT=5672
ENV SPRING_RABBITMQ_USERNAME=guest
ENV SPRING_RABBITMQ_PASSWORD=guest
ENV SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/notification_db
ENV USER_SERVICE_URL=http://user-service:8081

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]