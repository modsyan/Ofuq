# Use the official Maven image as a build stage
FROM maven:3.9.2-eclipse-temurin-17 as build

# Set the working directory for the build stage
WORKDIR /app

# Copy the pom.xml file and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Package the application into a JAR
RUN mvn clean package -DskipTests

# Use the JDK 17 runtime for the final image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory in the final image
WORKDIR /app

# Copy the JAR file from the build stage dynamically
COPY --from=build /app/target/*.jar /app/identity-service.jar

# Expose the port for the Identity Service
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/identity-service.jar"]
