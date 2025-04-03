package com.example.restaurantservice.controller.file;

import com.example.restaurantservice.service.file.BlobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:5173")
//@CrossOrigin(origins = "https://polite-pond-0844fed00.6.azurestaticapps.net")
@RestController
@RequiredArgsConstructor
@RequestMapping("/restaurant/file")
public class FileController {

    private final BlobService azureBlobService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@RequestPart MultipartFile file) {
        try {
            String url = azureBlobService.uploadFile(file);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}