# 第一阶段：构建 Spring Boot 项目
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# 设置工作目录
WORKDIR /app

# 复制 Maven 配置文件并预下载依赖（提高构建缓存效率）
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline

# Step 1: Use an official OpenJDK base image from Docker Hub
FROM openjdk:17

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Spring Boot JAR file into the container
COPY target/SpringMVCDemo-1.0-SNAPSHOT.jar /app/SpringMVCDemo-1.0-SNAPSHOT.jar

# Step 4: Expose the port your application runs on
EXPOSE 8080

# Step 5: Define the command to run your Spring Boot application
CMD ["java", "-jar", "/app/SpringMVCDemo-1.0-SNAPSHOT.jar"]
