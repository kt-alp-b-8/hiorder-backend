package com.example.orderservice.kafka.topic;

import lombok.Getter;

@Getter
public enum OrderTopic {
    OrdersReadyForPaymentEvent("orders-ready-for-payment-event");

    private String topic;

    OrderTopic(String topic) {
        this.topic = topic;
    }
}
