package com.example.orderservice.dto;

import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderInfoDto {

    private Long orderId;
    private Integer orderCode;          // ex) 1,2,3
    private Long tableId;               // 식당 테이블 pk
    private String orderTable;          // 주문 시점 테이블명
    private LocalDateTime createdAt;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private List<OrderItemInfoDto> items;

    public static OrderInfoDto of(Order order, List<OrderItemInfoDto> items) {
        return OrderInfoDto.builder()
                .orderId(order.getId())
                .orderCode(order.getOrderCode())
                .tableId(order.getTableId())
                .orderTable(order.getOrderTableName())
                .createdAt(order.getCreatedAt())
                .orderStatus(order.getOrderStatus())
                .totalAmount(order.getTotalAmount())
                .items(items)
                .build();
    }
}
