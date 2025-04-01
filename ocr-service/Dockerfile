FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server && npm install

COPY . .

# 인증서 포함되게 Docker Secret mount 주의
COPY start.sh ./start.sh
RUN chmod +x ./start.sh

CMD ["./start.sh"]
