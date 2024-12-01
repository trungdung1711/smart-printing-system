# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the host to the container
COPY target/smart-printing-system-0.0.1-SNAPSHOT.jar /app/smart-printing-system-0.0.1-SNAPSHOT.jar

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "smart-printing-system-0.0.1-SNAPSHOT.jar"]

# Expose the port that Spring Boot runs on
EXPOSE 8080
