package com.example.paymentservice.kafka.listener;

import com.example.paymentservice.kafka.event.OrdersReadyForPaymentEvent;
import com.example.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventListener {

    private final PaymentService paymentService;

    @KafkaListener(topics = "orders-ready-for-payment-event", groupId = "payment-group")
    public void handle(OrdersReadyForPaymentEvent event) {
        log.info("📥 주문 완료 이벤트 수신: {}", event);


    }
}
