FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server pm2 && npm install

COPY . .

# PM2 구성파일로 여러 서버 동시에 실행
CMD ["pm2-runtime", "pm2.config.js"]
