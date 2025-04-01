#!/bin/bash

# 1. 메인 웹 서버 (https)
http-server -p 8080 --cors -a 0.0.0.0 --ssl --cert localhost2.pem --key localhost2-key.pem &

# 2. proxy-server
cd proxy-server
node server.js &
cd ..

# 3. auth-status-server
cd auth-status-server
node server.js &
cd ..

# foreground 유지
wait
