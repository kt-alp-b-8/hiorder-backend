package com.example.ocrservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewForwardController {

    @GetMapping("/ocr/page/qr")
    public String showQrPage() {
        return "redirect:/ocr/page/qr.html";
    }

    @GetMapping("/ocr/page/index")
    public String showIndexPage() {
        return "redirect:/ocr/page/index.html";
    }
}
