FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server pm2 && npm install

COPY . .
COPY config ./config

CMD ["pm2-runtime", "config/pm2.config.js"]
