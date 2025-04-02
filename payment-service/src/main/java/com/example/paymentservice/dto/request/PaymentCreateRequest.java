package com.example.paymentservice.dto.request;

import com.example.paymentservice.entity.Payment;
import com.example.paymentservice.entity.Status;
import jakarta.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateRequest {

    private List<Long> orderIds;        // 연관된 주문 PK 리스트

    private Long tableId;               // 연관된 테이블 PK

    private String status;              // 결제 상태

    private String paymentKey;          // 결제 식별자 (토스 페이먼츠)

    public Payment toEntity() {
        return Payment.builder()
                .orderIds(orderIds)
                .tableId(tableId)
                .status(status)
                .paymentKey(paymentKey)
                .build();
    }
}
