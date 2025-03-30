package com.example.orderservice.controller;

import com.example.orderservice.controller.api.ApiResult;
import com.example.orderservice.dto.request.OrderCreateRequest;
import com.example.orderservice.dto.response.OrderCreateResponse;
import com.example.orderservice.dto.response.OrderStatusChangeResponse;
import com.example.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@Tag(name = "주문 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "주문 시, 주문이 생성된다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "식당명/테이블명이 조회된다.",
                    content = @Content(schema = @Schema(implementation = OrderCreateResponse.class)))
    })
    @PostMapping("/{restaurantId}/tables/{tableId}/orders")
    public ApiResult<?> createOrder(@PathVariable("restaurantId") Long restaurantId,
                                    @PathVariable("tableId") Long tableId,
                                    @RequestBody OrderCreateRequest orderCreateRequest) {

        return ApiResult.ok(HttpStatus.CREATED, orderService.createOrder(restaurantId, tableId, orderCreateRequest));

    }

    @Operation(summary = "주문 상태 수정", description = "사장님이 주문 상태를 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "손님 퇴장 후, 주문 상태를 변경한다.",
                    content = @Content(schema = @Schema(implementation = OrderStatusChangeResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "주문 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"주문 정보를 찾을 수 없습니다\"\"}")}
            ))
    })
    @PutMapping("/{restaurantId}/tables/{tableId}/orders/changeStatus")
    public ApiResult<?> changeOrderStatus(@PathVariable("restaurantId") Long restaurantId,
                                               @PathVariable("tableId") Long tableId) {

        return ApiResult.ok(HttpStatus.OK, orderService.changeOrderStatus(restaurantId, tableId));
    }


//    @GetMapping("/{restaurantId}/orders/history")
//    public ResponseEntity<?> getRestaurantOrderHistory(
//            @PathVariable("restaurantId") Long restaurantId,
//            @RequestParam(name="orderStatus", required=false, defaultValue="PENDING") String orderStatus,
//            @RequestParam(name="orderCode", required=false, defaultValue="desc") String orderCode
//    ) {
//        try {
//            RestaurantOrderHistoryResponse response = orderService.getRestaurantOrderHistory(restaurantId, orderStatus, orderCode);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            // 400
//            return ResponseEntity.badRequest().body(createErrorResponse(400, "요청 파라미터가 누락되었거나 형식이 올바르지 않습니다."));
//        } catch (RuntimeException e) {
//            // 404
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse(404, e.getMessage()));
//        } catch (Exception e) {
//            // 500
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse(500, "서버 내부 오류가 발생했습니다."));
//        }
//    }
//
//    private Object createErrorResponse(int status, String message) {
//        return new Object() {
//            public final int statusCode = status;
//            public final boolean success = false;
//            public final String msg = message;
//        };
//    }
//
//    @GetMapping("/{restaurantId}/tables/{tableId}/orders/history")
//    public ResponseEntity<?> getTableOrderHistory(
//            @PathVariable("restaurantId") Long restaurantId,
//            @PathVariable("tableId") Long tableId,
//            // [CHANGED] QueryParam name = orderStatus
//            @RequestParam(name="orderStatus", required=false, defaultValue="PENDING") String orderStatus,
//            // [CHANGED] QueryParam name = orderCode
//            @RequestParam(name="orderCode", required=false, defaultValue="desc") String orderCode,
//            @RequestParam(name="lang", required=false, defaultValue="kr") String lang // <-- 추가
//    ) {
//        try {
//            TableOrderHistoryResponse response = orderService.getTableOrderHistory(restaurantId, tableId, orderStatus, orderCode, lang);
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(createErrorResponse(400, "요청 파라미터가 누락되었거나 형식이 올바르지 않습니다."));
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse(404, e.getMessage()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(createErrorResponse(500, "서버 내부 오류가 발생했습니다."));
//        }
//    }
//
//    private Object createErrorResponse(int status, String message) {
//        return new Object() {
//            public final int statusCode = status;
//            public final boolean success = false;
//            public final String msg = message;
//        };
//    }
}
