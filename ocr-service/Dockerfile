FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server && npm install

COPY . .

# CMD에서 직접 서버들 실행
CMD http-server -p 8080 --cors -a 0.0.0.0 --ssl --cert localhost2.pem --key localhost2-key.pem & \
  node proxy-server/server.js & \
  node auth-status-server/server.js && \
  wait
