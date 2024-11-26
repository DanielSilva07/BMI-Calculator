FROM maven:3.9.9-eclipse-temurin-21-alpine AS  build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app
CMD ["chmod", "+x", "./mvnw"]
RUN mvn clean install -DsKipTests

FROM openjdk:17-ea-3-jdk-slim

WORKDIR /app
COPY --from=build app/target/imcApplication-0.0.1-SNAPSHOT.jar app/app.jar

ENTRYPOINT ["java" , "-jar" , "app/app.jar"]

