# Notification Service

## Mục lục

- [Cấu trúc dự án](#cấu-trúc-dự-án)
- [1. Cấu trúc Tổng thể](#1-cấu-trúc-tổng-thể)
- [2. Cấu hình Build và Dependencies](#2-cấu-hình-build-và-dependencies)
- [3. Ưu điểm của Kiến trúc](#3-ưu-điểm-của-kiến-trúc)
- [4. Quy ước và Tiêu chuẩn](#4-quy-ước-và-tiêu-chuẩn)
- [5. Cách chạy dự án với docker-compose](#5-cách-chạy-dự-án-với-docker-compose)


## Cấu trúc dự án

```
project
│
└───api  //Web Controller, Dto and SpringBoot configuration
│   └─── src
│   │ build.gradle
└─── common // Common Utilities
│   └─── src
│   │ build.gradle
└─── core  // Enterprise and Application Business Rules and Domain
│   └─── src
│   │ build.gradle
└─── infra // External, DB, UI and Device layer
│   └─── src
│   │ build.gradle
└─── common  // Common Utilities
│   └─── src
│   │ build.gradle`
│ build.gradle
│ settings.gradle
```
## 1. Cấu trúc Tổng thể
Dự án được chia thành 4 module chính:

### 1.1. Module API (api)
- **Mục đích**: Xử lý tầng giao tiếp web, cấu hình Spring Boot và các DTO  
- **Vai trò**:
  - Chứa các Web Controller  
  - Định nghĩa các DTO (Data Transfer Objects)  
  - Cấu hình Spring Boot  
- **Dependencies**: Phụ thuộc vào các module core, infra và common  

### 1.2. Module Core (core)
- **Mục đích**: Xử lý logic nghiệp vụ cốt lõi  
- **Vai trò**:
  - Chứa các business rules  
  - Định nghĩa domain model  
  - Xử lý logic nghiệp vụ chính  
- **Đặc điểm**: Độc lập với các framework bên ngoài  

### 1.3. Module Infrastructure (infra)
- **Mục đích**: Xử lý tương tác với các hệ thống bên ngoài  
- **Vai trò**:
  - Tương tác với Database  
  - Xử lý UI (nếu có)  
  - Tương tác với các thiết bị/dịch vụ bên ngoài  

### 1.4. Module Common (common)
- **Mục đích**: Cung cấp các tiện ích dùng chung  
- **Vai trò**:
  - Chứa các utility classes  
  - Định nghĩa các hằng số  
  - Cung cấp các helper function  

## 2. Cấu hình Build và Dependencies

### 2.1. Cấu hình Chung
- Sử dụng Gradle làm build tool  
- Java version: 21  
- Spring Boot version: 3.4.3  
- Spring Cloud version: 2024.0.0  

### 2.2. Các Plugin và Công cụ Chính
- Spotless: Format và kiểm tra code theo chuẩn  
- Spring Boot Gradle Plugin  
- Dependency Management Plugin  

### 2.3. Dependencies Chính
- Spring Boot Web Starter  
- Spring Boot Actuator  
- Log4j2  
- Lombok  
- MapStruct  
- Java JWT  
- Validation  

## 3. Ưu điểm của Kiến trúc

1. **Tách biệt Quan tâm (Separation of Concerns)**:
   - Mỗi module có trách nhiệm riêng biệt  
   - Dễ dàng bảo trì và phát triển  

2. **Khả năng Mở rộng**:
   - Có thể thêm module mới dễ dàng  
   - Dễ dàng thay đổi implementation  

3. **Tái sử dụng Code**:
   - Common module cho phép chia sẻ code  
   - Giảm thiểu code trùng lặp  

4. **Kiểm thử Dễ dàng**:
   - Các module độc lập dễ test  
   - Có thể mock các dependency  

## 4. Quy ước và Tiêu chuẩn

1. **Code Style**:
   - Sử dụng Google Java Format  
   - Tự động format code với Spotless  
   - Quản lý import tự động  

2. **Logging**:
   - Sử dụng Log4j2  
   - Loại bỏ logging mặc định của Spring Boot  

3. **Dependency Management**:
   - Sử dụng Spring Cloud BOM  
   - Quản lý version tập trung  

## 5. Cách chạy dự án với docker-compose

### 5.1. Yêu cầu hệ thống
- Docker Engine
- Docker Compose

### 5.2. Cấu trúc Docker
- **App Service**: Spring Boot application
  - Port: 8080 (external) -> 8080 (internal)
  - Phụ thuộc vào PostgreSQL service

- **PostgreSQL Service**:
  - Image: postgres:16-alpine
  - Port: 5432 (external) -> 5432 (internal)
  - Volume: postgres_data

### 5.3. Các bước chạy dự án

1. **Build project**:
   ```bash
   ./gradlew clean build
   ```

2. **Khởi động các service**:
   ```bash
   docker-compose up -d
   ```

3. **Kiểm tra trạng thái**:
   ```bash
   docker-compose ps
   ```

### 5.4. Cấu hình môi trường

Các biến môi trường chính trong docker-compose.yml:

```yaml
# App Service
- SPRING_PROFILES_ACTIVE=docker
- SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/notification_db
- SPRING_DATASOURCE_USERNAME=postgres
- SPRING_DATASOURCE_PASSWORD=postgres

# PostgreSQL Service
- POSTGRES_DB=notification_db
- POSTGRES_USER=postgres
- POSTGRES_PASSWORD=postgres
```

### 5.5. Truy cập dịch vụ

- **API Service**: http://localhost:8080/api/notification-service
- **Actuator Endpoints**:
  - Health Check: http://localhost:8080/actuator/health
  - Metrics: http://localhost:8080/actuator/prometheus

### 5.6. Quản lý Container

- **Dừng các service**:
  ```bash
  docker-compose down
  ```

- **Xem logs**:
  ```bash
  docker-compose logs -f
  ```

- **Khởi động lại service**:
  ```bash
  docker-compose restart [service_name]
  ```
