package com.example.orderservice.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    IN_PROGRESS("주문 진행중"), COMPLETED("결제 완료");

    private String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
