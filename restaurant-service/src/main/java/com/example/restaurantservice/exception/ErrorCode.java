package com.example.restaurantservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 400 BAD REQUEST
    MENU_RESTAURANT_NOT_MATCH(HttpStatus.BAD_REQUEST, "해당 식당에 속하는 메뉴가 아닙니다"),
    // 404 NOT FOUND
    RESTAURANT_NOT_FOUND(HttpStatus.NOT_FOUND, "식당 정보를 찾을 수 없습니다"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "카테고리 정보를 찾을 수 없습니다"),
    TABLE_NOT_FOUND(HttpStatus.NOT_FOUND, "테이블을 찾을 수 없습니다"),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다");

    // 409 CONFLICT


    // 500 INTERNAL_SERVER_ERROR


    private final HttpStatus httpStatus;
    private final String msg;
}