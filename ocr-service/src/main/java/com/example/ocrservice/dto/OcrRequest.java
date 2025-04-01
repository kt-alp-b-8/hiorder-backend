package com.example.ocrservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OcrRequest {
    private String version;
    private String requestId;
    private long timestamp;
    private List<OcrImage> images;

    @Getter
    @Setter
    public static class OcrImage {
        private String format;
        private String name;
        private String data; // base64 이미지 데이터
    }
}
