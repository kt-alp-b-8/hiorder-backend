package com.example.orderservice.kafka.publisher;

import com.example.orderservice.kafka.event.OrdersReadyForPaymentEvent;
import com.example.orderservice.kafka.topic.OrderTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrdersReadyForPaymentEvent(OrdersReadyForPaymentEvent event) {
        kafkaTemplate.send(OrderTopic.OrdersReadyForPaymentEvent.getTopic(), event);
    }
}
