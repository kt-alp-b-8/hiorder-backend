package com.example.orderservice.kafka.listener;

import com.example.orderservice.kafka.event.PaymentFailedEvent;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaEventListener {

    private final OrderService orderService;

    @KafkaListener(topics = "payment-failed-event", groupId = "payment-group")
    public void handle(PaymentFailedEvent event) {
        log.info("📥 결제 실패 이벤트 수신: {}", event);

        orderService.changeOrderStatusToInProgress(event.getOrderIds());
    }
}

