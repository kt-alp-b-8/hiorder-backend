FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server pm2 && npm install

COPY . .

# PM2로 3개 서버를 동시에 실행하고, http-server는 QR 페이지 제공
CMD pm2 start app.js \
    && pm2 start proxy-server/server.js --name proxy-server \
    && pm2 start auth-status-server/server.js --name auth-status-server \
    && http-server -p 8080 --cors
