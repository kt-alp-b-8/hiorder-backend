package com.example.paymentservice.controller.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"httpStatusCode", "message", "data"})
public class ApiResult<T> {

    private int httpStatusCode;
    private String message;
    private T data;

    // 에러
    private ApiResult(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    // 성공
    private ApiResult(int httpStatusCode, T data) {
        this.httpStatusCode = httpStatusCode;
        this.message = "요청에 성공하였습니다";
        this.data = data;
    }

    public static <T> ApiResult<T> error(HttpStatus httpStatus, String message) {
        return new ApiResult<>(httpStatus.value(), message);
    }

    public static <T> ApiResult<T> ok(HttpStatus httpStatus, T data) {
        return new ApiResult<>(httpStatus.value(), data);
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(HttpStatus.OK.value(), data);
    }
}