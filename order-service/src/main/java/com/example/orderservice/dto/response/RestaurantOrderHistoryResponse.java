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
public class RestaurantOrderHistoryResponse {

    private int status;
    private boolean success;
    private List<OrderInfo> data;
    private String message;

    @Getter
    @Builder
    public static class OrderInfo {
        private Long orderId;
        private Integer orderCode;   // ex) 1,2,3
        private Long tableId;        // 식당 테이블 pk
        private String orderTable;   // 주문 시점 테이블명
        private LocalDateTime createdAt;
        private String orderStatus;  // PENDING, PAID, etc.
        private Long totalAmount;
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
