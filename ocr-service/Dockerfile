FROM node:18

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

CMD http-server -p 8080 --cors -a 0.0.0.0 --ssl --cert localhost2.pem --key localhost2-key.pem & \
    node proxy-server/server.js & \
    node auth-status-server/server.js && \
    wait
