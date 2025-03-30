package com.example.orderservice.dto.request;


import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {

    private Long menuId;
    private String menuName;
    private int quantity;
    private int itemPrice;

    public OrderItem toEntity(Order order) {
        return OrderItem.builder()
                .quantity(quantity)
                .itemPrice(itemPrice)
                .menuName(menuName)
                .order(order)
                .menuId(menuId)
                .build();
    }
}
