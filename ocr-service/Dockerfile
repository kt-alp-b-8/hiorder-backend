FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server pm2 && npm install

COPY . ./
# 또는 아래처럼 명시적으로 보장해도 됨
# COPY . ./
# COPY pm2.config.js ./pm2.config.js

# PM2 구성 파일로 4개 서버 동시에 실행
CMD ["pm2-runtime", "pm2.config.js"]
