FROM node:18

WORKDIR /app

# 메인 패키지 설치
COPY package*.json ./
RUN npm install -g http-server

# 전체 복사
COPY . .

# 각각 의존성 설치
RUN cd proxy-server && npm install
RUN cd auth-status-server && npm install

# 실행
CMD http-server -p 8080 --cors -a 0.0.0.0 --ssl --cert localhost2.pem --key localhost2-key.pem & \
    node proxy-server/server.js & \
    node auth-status-server/server.js && \
    wait
