//package com.example.restaurantservice.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//@EnableWebMvc
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("http://**", "https://**", "ws://**")
//                .allowCredentials(true)
//                .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE", "PATCH")
//                .allowedHeaders("*");
//
//        WebMvcConfigurer.super.addCorsMappings(registry);
//    }
//}
