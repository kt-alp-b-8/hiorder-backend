package com.example.paymentservice.kafka.publisher;

import com.example.paymentservice.kafka.event.PaymentFailedEvent;
import com.example.paymentservice.kafka.topic.PaymentTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishPaymentFailedEvent(PaymentFailedEvent event) {
        kafkaTemplate.send(PaymentTopic.PaymentFailedEvent.getTopic(), event);
    }
}
