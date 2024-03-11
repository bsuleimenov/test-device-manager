# Use the official Maven image to build the application
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Create a new stage for the application runtime
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar test-device-manager.jar

# Expose the port on which your Spring Boot application runs
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "test-device-manager.jar"]
