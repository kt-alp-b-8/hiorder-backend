package com.example.ocrservice.service;

import com.example.ocrservice.dto.OcrRequest;
import com.example.ocrservice.dto.OcrResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;

@Service
@RequiredArgsConstructor
public class OcrService {

    public OcrResponse requestClova(OcrRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-OCR-SECRET", "Clova_Secret_Key");

        HttpEntity<OcrRequest> entity = new HttpEntity<>(request, headers);
        String CLOVA_URL = "https://bv1gaimcle.apigw.ntruss.com/custom/v1/...";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(CLOVA_URL, entity, String.class);

        // 실제 Clova 응답 처리 로직은 필요 시 구현
        OcrResponse result = new OcrResponse();
        result.setType("ID Card");
        result.setName("홍길동");
        result.setRrn("900101-1******");
        result.setAddress("서울특별시 ...");
        result.setIssueDate("2021-01-01");

        return result;
    }
}
