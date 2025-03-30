package com.example.orderservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableOrderHistoryResponse {

    private int status;
    private boolean success;
    private List<OrderInfo> data;
    private String message;

    @Getter
    @Builder
    public static class OrderInfo {
        private Long orderId;
        private Integer orderCode;
        private LocalDateTime createdAt;
        private Long totalAmount;
        private String orderStatus;
        private List<OrderItemInfo> items;
    }

    @Getter
    @Builder
    public static class OrderItemInfo {
        private Long menuId;
        private String menuName;
        private int quantity;
        private BigDecimal itemPrice;
    }
}
