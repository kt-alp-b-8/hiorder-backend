package com.example.orderservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "item_price", nullable = false)
    private int itemPrice;

    @Column(name = "menu_name", length = 100, nullable = false)
    private String menuName;

    @Column(name = "menu_id", nullable = false)
    private Long menuId;                          // 기존 다대일 -> msa 에서는 PK만 갖도록 변경

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Builder
    public OrderItem(Integer quantity, int itemPrice, String menuName, Long menuId, Order order) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.menuName = menuName;
        this.menuId = menuId;
        this.order = order;
    }
}
