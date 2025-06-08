# Build stage
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /app
# Copy the Maven project files
COPY pom.xml ./
# Download dependencies
RUN mvn dependency:go-offline -B
# Copy source code
COPY src ./src
# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM openjdk:17-ea-3-jdk-slim

WORKDIR /app
# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]