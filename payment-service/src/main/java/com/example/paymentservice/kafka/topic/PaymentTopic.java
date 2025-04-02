package com.example.paymentservice.kafka.topic;

import lombok.Getter;

@Getter
public enum PaymentTopic {
    PaymentFailedEvent("payment-failed-event");

    private String topic;

    PaymentTopic(String topic) {
        this.topic = topic;
    }
}
