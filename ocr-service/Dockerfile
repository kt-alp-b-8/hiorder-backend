FROM node:18

WORKDIR /app

# 패키지 설치용
COPY package*.json ./
RUN npm install -g http-server && npm install

# 전체 복사
COPY . .

# 세 개 서버 실행
CMD http-server -p 8080 --cors -a 0.0.0.0 --ssl --cert localhost2.pem --key localhost2-key.pem & \
    node proxy-server/server.js & \
    node auth-status-server/server.js && \
    wait
