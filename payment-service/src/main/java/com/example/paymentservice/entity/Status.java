package com.example.paymentservice.entity;

public enum Status {

    PENDING("결제 대기"),
    SUCCESS("결제 성공"),
    FAILED("결제 실패"),
    CANCELLED("결제 취소"),
    REFUNDED("환불 완료"),
    EXPIRED("결제 만료");

    private String name;

    Status(String name) {
        this.name = name;
    }
}
