# Use a base official Java 17 image
FROM openjdk:17-jdk-slim

# Variables
ARG JAR_FILE=target/*.jar

# Copy jar file into docker image
COPY ${JAR_FILE} gateway.jar

# Expose the port of the app in the docker container
EXPOSE 8080

# The ENTRYPOINT executes the java command to run the jar file when the docker image is run
ENTRYPOINT ["java","-jar","/gateway.jar"]
