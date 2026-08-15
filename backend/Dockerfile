# ============================
# BUILD STAGE
# ============================
FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x mvnw

COPY src src

RUN ./mvnw clean package -DskipTests


# ============================
# RUNTIME STAGE
# ============================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy Docker CLI from official Docker image
COPY --from=docker:27-cli /usr/local/bin/docker /usr/local/bin/docker

COPY --from=builder /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]