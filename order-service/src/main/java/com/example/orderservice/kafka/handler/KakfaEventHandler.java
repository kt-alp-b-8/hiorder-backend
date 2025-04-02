package com.example.orderservice.kafka.handler;

import com.example.orderservice.kafka.event.OrdersReadyForPaymentEvent;
import com.example.orderservice.kafka.publisher.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class KakfaEventHandler {

    private final KafkaEventPublisher eventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrdersReadyForPaymentEvent event) {
        eventPublisher.publishOrdersReadyForPaymentEvent(event);
    }
}
