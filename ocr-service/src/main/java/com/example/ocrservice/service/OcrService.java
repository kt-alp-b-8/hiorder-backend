package com.example.ocrservice.service;

import com.example.ocrservice.dto.OcrRequest;
import com.example.ocrservice.dto.OcrResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OcrService {

    private static final String CLOVA_URL = "https://bv1gaimcle.apigw.ntruss.com/custom/v1/40004/98ebbd00b8d1af11b184e8d830419073e28dfae2413024a30002111aac0aacce/document/id-card";
    private static final String OCR_SECRET = "SlpWQXVMQ1FWY0l2aXptT3FWWWtKcUdMVlFNUHlIekY=";

    public OcrResponse requestClova(OcrRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-OCR-SECRET", OCR_SECRET);

        HttpEntity<OcrRequest> httpEntity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(CLOVA_URL, HttpMethod.POST, httpEntity, Map.class);

            Map<String, Object> body = response.getBody();
            if (body == null || body.get("images") == null) {
                log.error("❌ OCR 응답에 'images' 필드 없음. body: {}", body);
                throw new RuntimeException("Clova OCR 응답에 이미지 정보가 없습니다.");
            }

            Map<String, Object> image = ((List<Map<String, Object>>) body.get("images")).get(0);
            Map<String, Object> idCard = (Map<String, Object>) image.get("idCard");
            Map<String, Object> result = (Map<String, Object>) idCard.get("result");

            if (result == null || result.isEmpty()) {
                log.warn("⚠️ OCR 결과(result)가 비어있습니다.");
                throw new RuntimeException("신분증 인식 결과가 없습니다.");
            }

            if (result.containsKey("dl")) {
                Map<String, Object> dl = (Map<String, Object>) result.get("dl");
                return new OcrResponse(
                        "Driver's License",
                        extract(dl, "name"),
                        maskRRN(extract(dl, "personalNum")),
                        extract(dl, "address"),
                        extract(dl, "issueDate"),
                        extract(dl, "num"),
                        extract(dl, "renewStartDate"),
                        extract(dl, "renewEndDate"),
                        extract(dl, "condition")
                );
            } else if (result.containsKey("ic")) {
                Map<String, Object> ic = (Map<String, Object>) result.get("ic");
                return new OcrResponse(
                        "ID Card",
                        extract(ic, "name"),
                        maskRRN(extract(ic, "personalNum")),
                        extract(ic, "address"),
                        extract(ic, "issueDate"),
                        null, null, null, null
                );
            } else {
                log.warn("⚠️ OCR 결과에 'dl' 또는 'ic'가 존재하지 않음. result: {}", result);
                throw new RuntimeException("지원하지 않는 신분증 형식입니다.");
            }

        } catch (HttpStatusCodeException e) {
            log.error("❌ Clova OCR API HTTP 오류: {}", e.getResponseBodyAsString(), e);
            throw new RuntimeException("Clova OCR API 호출에 실패했습니다.");
        } catch (Exception e) {
            log.error("❌ Clova OCR 처리 중 예외 발생", e);
            throw new RuntimeException("OCR 처리 중 알 수 없는 오류가 발생했습니다.");
        }
    }


    private String extract(Map<String, Object> map, String key) {
        try {
            return (String) ((Map<String, Object>) ((java.util.List<Object>) map.get(key)).get(0)).get("text");
        } catch (Exception e) {
            return "정보 없음";
        }
    }

    private String maskRRN(String rrn) {
        if (rrn == null || rrn.length() < 7) return rrn;
        return rrn.substring(0, 6) + "-" + rrn.charAt(6) + "******";
    }
}
