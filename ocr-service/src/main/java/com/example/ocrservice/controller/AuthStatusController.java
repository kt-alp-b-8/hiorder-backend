package com.example.ocrservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthStatusController {

    private final AtomicInteger authCount = new AtomicInteger(0);

    @PostMapping("/increment")
    public Map<String, Integer> incrementAuthCount() {
        int count = authCount.incrementAndGet();
        log.info("인증된 사용자 수 증가: {}", count);
        return Map.of("count", count);
    }

    @GetMapping("/count")
    public Map<String, Integer> getAuthCount() {
        int count = authCount.get();
        log.info("현재 인증된 사용자 수: {}", count);
        return Map.of("count", count);
    }
}
