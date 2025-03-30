package com.example.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusChangeResponse {

    private int updatedCount;        // 몇 건 변경
    private List<Long> updatedOrderIds; // 어떤 주문들이 변경됐는지

    public static OrderStatusChangeResponse of(int updatedCount, List<Long> updatedOrderIds) {
        return OrderStatusChangeResponse.builder()
                .updatedCount(updatedCount)
                .updatedOrderIds(updatedOrderIds)
                .build();
    }
}
