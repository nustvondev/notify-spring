# Stage 1: Build với Gradle và JDK 21
FROM gradle:jdk21-jammy AS builder

# Set timezone
ENV TZ=Asia/Ho_Chi_Minh

# Copy gradle files đầu tiên để tận dụng Docker cache
# COPY settings.gradle build.gradle ./
# COPY api/build.gradle api/
# COPY core/build.gradle core/
# COPY common/build.gradle common/

# Copy source code
# COPY api api/
# COPY core core/
# COPY common common/
COPY . /home/gradle

# Build ứng dụng
RUN gradle :api:build --no-daemon

# Stage 2: Runtime với JRE
FROM eclipse-temurin:21-jre

# Set timezone
ENV TZ=Asia/Ho_Chi_Minh

# Tạo thư mục app
WORKDIR /app

# Copy JAR từ stage build
COPY --from=builder /home/gradle/api/build/ /app
# Port mà ứng dụng sẽ lắng nghe
EXPOSE 8080

# Command để chạy ứng dụng
ENTRYPOINT ["java","-jar","/app/libs/api-0.0.1-SNAPSHOT.jar"]