package com.example.restaurantservice.controller.restaurant;

import com.example.restaurantservice.controller.api.ApiResult;
import com.example.restaurantservice.dto.response.RestaurantInfoResponse;
import com.example.restaurantservice.service.restaurant.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(summary = "식당 정보 조회", description = "식당, 테이블명을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "식당명/테이블명이 조회된다.",
                    content = @Content(schema = @Schema(implementation = RestaurantInfoResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "레스토랑 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"식당 정보를 찾을 수 없습니다\"\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "테이블 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"테이블을 찾을 수 없습니다\"\"}")}
            ))
    })
    @GetMapping("/{restaurantId}/table/{tableId}")
    public ApiResult<?> getRestaurantInfo(@PathVariable("restaurantId") Long restaurantId,
                                          @PathVariable("tableId") Long tableId) {

        return ApiResult.ok(HttpStatus.OK, restaurantService.getRestaurantInfo(restaurantId, tableId));
    }

}
