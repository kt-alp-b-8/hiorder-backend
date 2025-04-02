package com.example.orderservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersReadyForPaymentEvent {

    private List<Long> orderIds;

    public static OrdersReadyForPaymentEvent of(List<Long> orderIds) {
        return OrdersReadyForPaymentEvent.builder()
                .orderIds(orderIds)
                .build();
    }
}
