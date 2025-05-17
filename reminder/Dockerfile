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
ENV SPRING_DATA_MONGODB_URI=mongodb://mongodb:27017/reminder_db

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]