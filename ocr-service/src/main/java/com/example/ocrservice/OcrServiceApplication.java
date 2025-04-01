package com.example.ocrservice;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class OcrServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OcrServiceApplication.class, args);
    }

    @PostConstruct
    public void startNodeProxy() {
        try {
            ProcessBuilder builder = new ProcessBuilder("node", "server.js");
            builder.directory(new File("node")); // node 폴더 기준
            builder.redirectErrorStream(true);
            Process process = builder.start();

            // 로그 보기
            new Thread(() -> {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("Node.js >> " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            System.out.println("✅ Node server.js 실행됨");

        } catch (IOException e) {
            System.err.println("❌ Node 서버 실행 실패: " + e.getMessage());
        }
    }



//    @PostConstruct
//    public void startNodeServer() {
//        try {
//            ProcessBuilder pb = new ProcessBuilder("node", "server.js"); // 👈 요거!
//            pb.directory(new File("node"));
//            pb.inheritIO();
//            pb.start();
//            System.out.println("✅ Node server.js 실행됨");
//        } catch (IOException e) {
//            System.err.println("❌ Node 서버 실행 실패: " + e.getMessage());
//        }
//    }

}
