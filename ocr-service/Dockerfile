FROM node:18-alpine

WORKDIR /app

COPY . .

# 필요한 경우 인증서 설치
RUN apk add --no-cache openssl

# 각 서브 폴더 의존성 설치
RUN cd proxy-server && npm install && cd ..
RUN cd auth-status-server && npm install && cd ..

# 메인 프로젝트 의존성
RUN npm install -g http-server

# QR 서버가 8080 포트에서 index.html/qr.html을 제공하므로, 8080을 기본 포트로
EXPOSE 8080
EXPOSE 3000

CMD ["sh", "-c", "node proxy-server/server.js & node auth-status-server/server.js & http-server -p 8080 --cors"]

