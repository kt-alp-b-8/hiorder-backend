package com.example.orderservice.dto.request;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private String orderTable;
    private List<OrderItemRequestDto> orderItemRequestDtos;
    private long totalAmount; // ex) 26000
    private int peopleCount;

    public Order toEntity(OrderStatus initOrderStatus, Long restaurantId, Long tableId, int orderCode) {
        return Order.builder()
                .orderStatus(initOrderStatus)
                .totalAmount(totalAmount)
                .orderCode(orderCode)
                .orderTableName(orderTable)
                .restaurantId(restaurantId)
                .tableId(tableId)
                .peopleCount(peopleCount)
                .build();
    }
}
