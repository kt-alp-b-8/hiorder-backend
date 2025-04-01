FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server pm2 && npm install

COPY . ./

# ✅ pm2.config.js가 안 들어가는 이슈 방지 (명시적 복사)
COPY pm2.config.js ./pm2.config.js

CMD ["pm2-runtime", "pm2.config.js"]
