package com.example.orderservice.dto.response;

import com.example.orderservice.dto.OrderInfoDto;
import com.example.orderservice.dto.OrderItemInfoDto;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderStatus;
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

    private List<OrderInfoDto> data;

//    private int status;
//    private boolean success;
//    private List<OrderInfoDto> data;
//    private String message;
//
//    @Getter
//    @Builder
//    public static class OrderInfo {
//        private Long orderId;
//        private Integer orderCode;
//        private LocalDateTime createdAt;
//        private Long totalAmount;
//        private String orderStatus;
//        private List<OrderItemInfo> items;
//    }
//
//
//    @Getter
//    @Builder
//    public static class OrderItemInfo {
//        private Long menuId;
//        private String menuName;
//        private int quantity;
//        private BigDecimal itemPrice;
//    }

    public static TableOrderHistoryResponse of(List<OrderInfoDto> data) {
        return TableOrderHistoryResponse.builder()
                .data(data)
                .build();
    }
}
