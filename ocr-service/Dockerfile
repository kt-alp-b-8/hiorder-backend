# 1. Base image (Java & Node.js 설치된 이미지 사용)
FROM openjdk:17-jdk-slim as builder

# Node.js 설치
RUN apt-get update && apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs && \
    node -v && npm -v

# 2. 작업 디렉토리 생성
WORKDIR /app

# 3. Spring Boot JAR 복사
COPY build/libs/*.jar app.jar

# 4. Node.js 소스 복사
COPY node ./node

# 5. Node.js 의존성 설치
WORKDIR /app/node
RUN npm install

# 6. 다시 JAR 실행 위치로 복귀
WORKDIR /app

# 7. 실행 (Spring Boot 내에서 Node.js를 ProcessBuilder로 실행하도록 이미 설정되어 있으므로, JAR만 실행)
CMD ["java", "-jar", "app.jar"]
