package com.example.paymentservice.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentFailedEvent {

    private List<Long> orderIds;

    public static PaymentFailedEvent of(List<Long> orderIds) {
        return PaymentFailedEvent.builder()
                .orderIds(orderIds)
                .build();
    }
}
