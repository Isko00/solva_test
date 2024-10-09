# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the host to the container
COPY build/libs/solva_test-1.0-SNAPSHOT.jar app.jar

# Expose the port your application runs on
EXPOSE 8081

# Define the command to run your application
ENTRYPOINT ["java", "-jar", "app.jar"]
