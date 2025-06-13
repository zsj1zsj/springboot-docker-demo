# 第一阶段：构建 Spring Boot 项目
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# 设置工作目录
WORKDIR /app

# 复制 Maven 配置文件并预下载依赖（提高构建缓存效率）
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN ./mvnw dependency:go-offline

# 复制项目源码
COPY src ./src

# 构建项目
RUN ./mvnw clean package -DskipTests

# 第二阶段：运行构建好的 jar 文件
FROM eclipse-temurin:17-jdk-alpine

# 设置工作目录
WORKDIR /app
echo "# springboot-docker-demo" >> README.md
# 拷贝构建好的 jar 包（假设在 target 目录下）
COPY --from=builder /app/target/*.jar app.jar

# 暴露端口（修改为你的应用端口）
EXPOSE 8080

# 启动 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "app.jar"]
