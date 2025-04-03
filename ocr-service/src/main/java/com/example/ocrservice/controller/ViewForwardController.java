package com.example.ocrservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

//@CrossOrigin(origins = "http://localhost:5173")
//@CrossOrigin(origins = "https://polite-pond-0844fed00.6.azurestaticapps.net")
@Controller
public class ViewForwardController {

    @GetMapping("/ocr/page/qr")
    public String showQrPage() {
        return "forward:/ocr/page/qr.html";
    }

    @GetMapping("/ocr/page/index")
    public String showIndexPage() {
        return "forward:/ocr/page/index.html";
    }
}
