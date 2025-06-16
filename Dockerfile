# Dockerfile
FROM openjdk:17-jdk-slim
VOLUME /tmp
WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .
COPY src/ src/

RUN chmod +x mvnw && ./mvnw clean package -DskipTests

EXPOSE 8080
CMD ["java", "-jar", "target/*.jar"]
