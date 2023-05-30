# Use a base official Java 17 image
FROM openjdk:17-jdk-slim

# Variables
ARG JAR_FILE=target/auth.jar
ARG RSA_PRIVATE_KEY_FILE=src/main/resources/certs/private.pem
ARG RSA_PUBLIC_KEY_FILE=src/main/resources/certs/public.pem

# Copy jar file into docker image
COPY ${JAR_FILE} auth.jar

# Copy RSA keys into docker image
COPY ${RSA_PRIVATE_KEY_FILE} resources/certs/private.pem
COPY ${RSA_PUBLIC_KEY_FILE} resources/certs/public.pem

# Expose the port of the app in the docker container
EXPOSE 8083

# The ENTRYPOINT executes the java command to run the jar file when the docker image is run
ENTRYPOINT ["java","-jar","/auth.jar"]
