FROM openjdk:25-ea-4-jdk-oraclelinux9

WORKDIR /app

COPY target/user-0.0.1-SNAPSHOT.jar UserService.jar

EXPOSE 8085

ENTRYPOINT ["java", "-jar","UserService.jar"]