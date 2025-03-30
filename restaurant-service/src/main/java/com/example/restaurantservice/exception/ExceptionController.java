package com.example.restaurantservice.exception;

import com.example.restaurantservice.controller.api.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExceptionController {

    @ExceptionHandler(CustomException.class)
    public ApiResult<?> handleCustomException(CustomException e){

        log.error(e.getErrorCode().getMsg(), e.getStackTrace()[0]);        // 인덱스 0 -> 실제 예외 발생 지점

        return ApiResult.error(e.getErrorCode().getHttpStatus(), e.getErrorCode().getMsg());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ApiResult<?> bindException(BindException e){

        ObjectError error = e.getBindingResult().getAllErrors().get(0);

        return ApiResult.error(HttpStatus.BAD_REQUEST, error.getDefaultMessage());
    }

}
