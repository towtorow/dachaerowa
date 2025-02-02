# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Build arguments
ARG JWT_SECRET_KEY
ARG DATABASE_URL
ARG DATABASE_USER
ARG DATABASE_PW
ARG AWS_S3_ACCESS_KEY
ARG AWS_S3_SECRET_KEY
ARG AWS_REGION

# Set environment variables
ENV JWT_SECRET_KEY=${JWT_SECRET_KEY}
ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USER=${DATABASE_USER}
ENV DATABASE_PW=${DATABASE_PW}
ENV AWS_S3_ACCESS_KEY=${AWS_S3_ACCESS_KEY}
ENV AWS_S3_SECRET_KEY=${AWS_S3_SECRET_KEY}
ENV AWS_REGION=${AWS_REGION}

# Set the working directory
WORKDIR /app

# Add the application's JAR file to the container
COPY build/libs/*.jar app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
