package com.example.orderservice.dto;

import com.example.orderservice.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemInfoDto {

    private Long menuId;
    private String menuName;
    private int quantity;
    private int itemPrice;

    public static OrderItemInfoDto of(OrderItem orderItem) {
        return OrderItemInfoDto.builder()
                .menuId(orderItem.getMenuId())
                .menuName(orderItem.getMenuName())
                .quantity(orderItem.getQuantity())
                .itemPrice(orderItem.getItemPrice())
                .build();
    }
}
