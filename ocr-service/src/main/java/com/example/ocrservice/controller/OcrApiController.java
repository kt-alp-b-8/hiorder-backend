package com.example.ocrservice.controller;

import com.example.ocrservice.dto.OcrRequest;
import com.example.ocrservice.dto.OcrResponse;
import com.example.ocrservice.service.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
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
