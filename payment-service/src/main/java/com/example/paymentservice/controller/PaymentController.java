package com.example.paymentservice.controller;

import com.example.paymentservice.controller.api.ApiResult;
import com.example.paymentservice.dto.request.PaymentCreateRequest;
import com.example.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
//@CrossOrigin(origins = "https://polite-pond-0844fed00.6.azurestaticapps.net")
@Slf4j
@Tag(name = "결제 API")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("")
    public ApiResult<?> createPayment(@RequestBody PaymentCreateRequest paymentCreateRequest) {

        paymentService.createPayment(paymentCreateRequest);

        return ApiResult.ok(HttpStatus.CREATED);
    }
}
