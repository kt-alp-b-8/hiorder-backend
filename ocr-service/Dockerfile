FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server pm2 && npm install

COPY . .

# 인증서는 쿠버네티스에서 마운트
# ENTRYPOINT를 통해 모든 서버 동시 실행
CMD ["pm2-runtime", "start", "ecosystem.config.js"]
