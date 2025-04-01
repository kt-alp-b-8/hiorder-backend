FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install -g http-server && npm install

COPY . .

COPY start.sh ./start.sh
RUN chmod +x ./start.sh

CMD ["sh", "./start.sh"]