package com.example.paymentservice.kafka.handler;

import com.example.paymentservice.kafka.event.PaymentFailedEvent;
import com.example.paymentservice.kafka.publisher.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class KafkaEventHandler {

    private final KafkaEventPublisher eventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(PaymentFailedEvent event) {
        eventPublisher.publishPaymentFailedEvent(event);
    }
}
