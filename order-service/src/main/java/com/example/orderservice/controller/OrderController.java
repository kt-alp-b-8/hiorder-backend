package com.example.orderservice.controller;

import com.example.orderservice.controller.api.ApiResult;
import com.example.orderservice.dto.request.OrderCreateRequest;
import com.example.orderservice.dto.response.OrderCreateResponse;
import com.example.orderservice.dto.response.OrderStatusChangeResponse;
import com.example.orderservice.dto.response.RestaurantOrderHistoryResponse;
import com.example.orderservice.dto.response.TableOrderHistoryResponse;
import com.example.orderservice.entity.OrderStatus;
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
@CrossOrigin(
        origins = "https://polite-pond-0844fed00.6.azurestaticapps.net",
        methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
//@CrossOrigin(
//        origins = "http://localhost:5173",
//        methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
//)
@Slf4j
@Tag(name = "주문 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "주문 시, 주문이 생성된다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "식당명/테이블명이 조회된다.",
                    content = @Content(schema = @Schema(implementation = OrderCreateResponse.class)))
    })
    @PostMapping("/{restaurantId}/table/{tableId}/order")
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
    @PutMapping("/{restaurantId}/table/{tableId}/order/changeStatus")
    public ApiResult<?> changeOrderStatus(@PathVariable("restaurantId") Long restaurantId,
                                          @PathVariable("tableId") Long tableId) {

        return ApiResult.ok(HttpStatus.OK, orderService.changeOrderStatus(restaurantId, tableId));
    }

    @Operation(summary = "식당 주문 내역 조회", description = "사장님이 주문 내역을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "",
                    content = @Content(schema = @Schema(implementation = RestaurantOrderHistoryResponse.class)))
    })
    @GetMapping("/{restaurantId}/order/history")
    public ApiResult<?> getRestaurantOrderHistory(@PathVariable("restaurantId") Long restaurantId,
                                                       @RequestParam(name="orderStatus", required=false, defaultValue="IN_PROGRESS") String orderStatus,
                                                       @RequestParam(name="orderCode", required=false, defaultValue="desc") String orderCode) {

        return ApiResult.ok(
                HttpStatus.OK,
                orderService.getRestaurantOrderHistory(restaurantId, OrderStatus.valueOf(orderStatus), orderCode)
        );
    }

    @Operation(summary = "테이블 별 주문 내역 조회", description = "사장님이 주문 상태를 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "손님 퇴장 후, 주문 상태를 변경한다.",
                    content = @Content(schema = @Schema(implementation = TableOrderHistoryResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "주문 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"주문 정보를 찾을 수 없습니다\"\"}")}
            ))
    })
    @GetMapping("/{restaurantId}/table/{tableId}/order/history")
    public ApiResult<?> getTableOrderHistory(@PathVariable("restaurantId") Long restaurantId,
                                             @PathVariable("tableId") Long tableId,
                                             @RequestParam(name="orderStatus", required=false, defaultValue="IN_PROGRESS") String orderStatus,
                                             @RequestParam(name="orderCode", required=false, defaultValue="desc") String orderCode,
                                             @RequestParam(name="lang", required=false, defaultValue="kr") String lang) {

        return ApiResult.ok(
                HttpStatus.OK,
                orderService.getTableOrderHistory(restaurantId, tableId, OrderStatus.valueOf(orderStatus), orderCode, lang)
        );

    }

}
