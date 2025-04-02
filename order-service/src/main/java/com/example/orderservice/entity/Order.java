package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "total_amount", nullable = false)
    private long totalAmount;  // or BigDecimal

    @Column(name = "order_code", nullable = false)
    private Integer orderCode; // 혹은 String, depending on usage // 주문 코드 (매일 식당별로 리셋)

    // "table_id"와 "order_table"를 동시에 두는 이유는,
    // DB에서 테이블명 변경되어도 과거 주문내역에 영향 안주도록 "order_table" 별도 보존
    @Column(name = "order_table_name", length = 40, nullable = false)
    private String orderTableName;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;                          // 기존 다대일 -> msa 에서는 PK만 갖도록 변경

    @Column(name = "table_id", nullable = false)
    private Long tableId;                               // 기존 다대일 -> msa 에서는 PK만 갖도록 변경

    private int peopleCount;

    @Builder
    public Order(OrderStatus orderStatus, long totalAmount, Integer orderCode, String orderTableName, Long restaurantId, Long tableId, int peopleCount) {
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.orderCode = orderCode;
        this.orderTableName = orderTableName;
        this.restaurantId = restaurantId;
        this.tableId = tableId;
        this.peopleCount = peopleCount;
    }

    //== 비지니스 로직 ==//
    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
