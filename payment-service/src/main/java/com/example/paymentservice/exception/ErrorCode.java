package com.example.paymentservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 BAD REQUEST


    // 404 NOT FOUND
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다");

    // 409 CONFLICT


    // 500 INTERNAL_SERVER_ERROR


    private final HttpStatus httpStatus;
    private final String msg;
}