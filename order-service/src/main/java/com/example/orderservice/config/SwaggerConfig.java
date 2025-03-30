package com.example.orderservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){

        Info info = new Info()
                .title("KT AI 하이오더 주문 도메인 API Document")
                .description("KT-ALP 8조 AI 하이오더 시스템 주문 도메인 API 문서");

        return new OpenAPI()
                .info(info);
    }
}
