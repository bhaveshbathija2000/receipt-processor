# Use OpenJDK 22 as base image
FROM openjdk:22-jdk-slim

# Set working directory
WORKDIR /app

# Copy the fat JAR file into the container
COPY target/receipt-processor-0.0.1-SNAPSHOT.jar app.jar

# Expose application port
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]