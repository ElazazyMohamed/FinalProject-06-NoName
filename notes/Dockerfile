FROM eclipse-temurin:23-jre

WORKDIR /app

# Copy the pre-built JAR file
COPY target/notes-*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]
