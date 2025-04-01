FROM node:18

WORKDIR /app

# 인증서 복사 (GitHub Actions에서 미리 준비한 상태)
COPY localhost+2.pem localhost+2.pem
COPY localhost+2-key.pem localhost+2-key.pem

# 메인 서버
COPY app.js index.html qr.html package*.json ./

# 프록시 서버
COPY proxy-server ./proxy-server

# 인증 상태 서버
COPY auth-status-server ./auth-status-server

RUN npm install
RUN cd proxy-server && npm install
RUN cd auth-status-server && npm install

EXPOSE 8080 3000

CMD ["sh", "-c", "node proxy-server/server.js & node auth-status-server/server.js & node app.js"]
