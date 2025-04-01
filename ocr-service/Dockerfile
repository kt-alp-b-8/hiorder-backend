# Node.js 기반 이미지 사용
FROM node:18

# 작업 디렉토리 생성
WORKDIR /app

# 의존성 설치
COPY package*.json ./
RUN npm install

# 모든 파일 복사
COPY . .

# HTTPS 통신 위해 포트 3000 (프록시), 3002 (인증 상태 관리) 오픈
EXPOSE 3000
EXPOSE 3002

# 서버 실행
CMD ["node", "server.js"]
