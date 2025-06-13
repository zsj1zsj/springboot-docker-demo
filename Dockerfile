# 第一阶段：用 Maven 构建
FROM maven:3.9.5-eclipse-temurin-17 AS builder

WORKDIR /app

# 只复制 Maven 配置和源代码
COPY pom.xml .
COPY src ./src

# 编译 JAR 包（跳过测试）
RUN mvn clean package -DskipTests

# 第二阶段：运行 JAR
FROM openjdk:17

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
