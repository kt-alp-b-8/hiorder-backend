package com.example.paymentservice.service;

import com.example.paymentservice.controller.api.ApiResult;
import com.example.paymentservice.dto.request.PaymentCreateRequest;
import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.kafka.event.PaymentFailedEvent;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void createPayment(PaymentCreateRequest paymentCreateRequest) {

        Payment payment = paymentCreateRequest.toEntity();
        paymentRepository.save(payment);

        if(!payment.getStatus().equals("paid")){     // 결제 실패 -> 보상 트랜잭션 수행
            applicationEventPublisher.publishEvent(PaymentFailedEvent.of(payment.getOrderIds()));
        }
    }
}
