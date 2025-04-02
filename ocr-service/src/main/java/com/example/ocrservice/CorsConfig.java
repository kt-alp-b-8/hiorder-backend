package com.example.ocrservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 경로 허용
                        .allowedOrigins("*") // 모든 Origin 허용 (배포시 특정 도메인으로 제한 추천)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true) // 쿠키 전송 등 인증 정보 허용 시 true
                        .maxAge(3600); // preflight 캐시 시간 (초)
            }
        };
    }
}
