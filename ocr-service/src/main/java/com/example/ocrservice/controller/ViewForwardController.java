package com.example.ocrservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewForwardController {

    @GetMapping("/ocr/qr")
    public String showQrPage() {
        return "forward:/ocr/qr.html";
    }

    @GetMapping("/ocr/index")
    public String showIndexPage() {
        return "forward:/ocr/index.html";
    }
}
