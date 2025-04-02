package com.example.paymentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "payment")
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @ElementCollection
    private List<Long> orderIds;        // 연관된 주문 PK 리스트

    private Long tableId;               // 연관된 테이블 PK

//    private Status status;              // 결제 상태
    private String status;

    private String paymentKey;          // 결제 식별자 (토스 페이먼츠)

    @Builder
    public Payment(List<Long> orderIds, Long tableId, String status, String paymentKey) {
        this.orderIds = orderIds;
        this.tableId = tableId;
        this.status = status;
        this.paymentKey = paymentKey;
    }
}
