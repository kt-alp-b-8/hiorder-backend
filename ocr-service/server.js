const express = require("express");
const cors = require("cors");
const axios = require("axios");
const https = require("https");
const fs = require("fs");
const path = require("path");

const certOptions = {
    key: fs.readFileSync(path.join(__dirname, "localhost+2-key.pem")),
    cert: fs.readFileSync(path.join(__dirname, "localhost+2.pem")),
};

// 인증 상태 서버용 Express 앱
const authApp = express();
authApp.use(cors({ origin: "*", methods: ["GET", "POST"], credentials: true }));
authApp.use(express.json());

let authenticatedCount = 0;

authApp.post("/increment", (req, res) => {
    authenticatedCount++;
    console.log("인증된 사용자 수 증가:", authenticatedCount);
    res.json({ count: authenticatedCount });
});

authApp.get("/count", (req, res) => {
    console.log("현재 인증된 사용자 수:", authenticatedCount);
    res.json({ count: authenticatedCount });
});

https.createServer(certOptions, authApp).listen(3002, "0.0.0.0", () => {
    console.log("Auth status server running at https://0.0.0.0:3002");
});

// OCR 프록시 서버용 Express 앱
const proxyApp = express();
proxyApp.use(cors({
    origin: "*",
    methods: ["GET", "POST", "OPTIONS"],
    allowedHeaders: ["Content-Type", "X-OCR-SECRET"],
    credentials: true,
}));
proxyApp.use(express.json({ limit: "50mb" }));

proxyApp.post("/proxy/ocr", async (req, res) => {
    try {
        console.log("OCR 요청 받음");
        const response = await axios.post(
            "https://bv1gaimcle.apigw.ntruss.com/custom/v1/40004/98ebbd00b8d1af11b184e8d830419073e28dfae2413024a30002111aac0aacce/document/id-card",
            req.body,
            {
                headers: {
                    "Content-Type": "application/json",
                    "X-OCR-SECRET": "SlpWQXVMQ1FWY0l2aXptT3FWWWtKcUdMVlFNUHlIekY=",
                },
            }
        );
        console.log("OCR 응답 성공");
        res.json(response.data);
    } catch (error) {
        console.error("OCR API 에러:", error);
        res.status(500).json({ error: "Failed to process OCR request", details: error.response?.data || error.message });
    }
});

https.createServer(certOptions, proxyApp).listen(3000, "0.0.0.0", () => {
    console.log("Proxy server running at https://0.0.0.0:3000");
});
