package com.example.orderservice.dto.response;


import com.example.orderservice.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDto {

    private Long orderItemId;
    private Long menuId;
    private String menuName;
    private int quantity;
    private int itemPrice;

    public static OrderItemResponseDto of(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .orderItemId(orderItem.getId())
                .menuId(orderItem.getMenuId())
                .menuName(orderItem.getMenuName())
                .quantity(orderItem.getQuantity())
                .itemPrice(orderItem.getItemPrice())
                .build();

    }
}
