FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server pm2 && npm install

COPY . .                            # 기존 전체 복사
COPY config/pm2.config.js ./config/ # ✅ 확실하게 복사

CMD ["pm2-runtime", "config/pm2.config.js"]
