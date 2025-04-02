package com.example.ocrservice.controller;

import com.example.ocrservice.dto.OcrRequest;
import com.example.ocrservice.dto.OcrResponse;
import com.example.ocrservice.service.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ocr/api/ocr")
@RequiredArgsConstructor
public class OcrApiController {

    private final OcrService ocrService;

    @PostMapping
    public ResponseEntity<OcrResponse> handleOcr(@RequestBody OcrRequest request) {
        OcrResponse response = ocrService.requestClova(request);
        return ResponseEntity.ok(response);
    }
}
