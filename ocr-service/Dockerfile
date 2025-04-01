FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server pm2 && npm install

COPY . .

# ✅ 명시적 경로로 변경
CMD ["pm2-runtime", "config/pm2.config.js"]
