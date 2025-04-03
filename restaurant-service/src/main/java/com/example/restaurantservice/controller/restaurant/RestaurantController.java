package com.example.restaurantservice.controller.restaurant;

import com.example.restaurantservice.controller.api.ApiResult;
import com.example.restaurantservice.dto.response.RestaurantInfoResponse;
import com.example.restaurantservice.dto.response.RestaurantMenuInfoResponse;
import com.example.restaurantservice.dto.response.RestaurantTableInfoResponse;
import com.example.restaurantservice.service.menu.MenuService;
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
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(
        origins = "https://polite-pond-0844fed00.6.azurestaticapps.net",
        methods = {RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
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

    @Operation(summary = "식당 카테고리 정보 조회", description = "식당의 메뉴 카테고리 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "식당의 메뉴 카테고리 정보가 조회된다.",
                    content = @Content(schema = @Schema(implementation = RestaurantInfoResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "레스토랑 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"식당 정보를 찾을 수 없습니다\"\"}")}
            ))
    })
    @GetMapping("/{restaurantId}/category")
    public ApiResult<?> getRestaurantCategories(@PathVariable("restaurantId") Long restaurantId,
                                                @RequestParam(name = "sort", required = false) String sortParam,
                                                @RequestParam(name = "lang", required = false, defaultValue = "kr") String lang) {

        return ApiResult.ok(HttpStatus.OK, restaurantService.getMenuCategoriesByLang(restaurantId, sortParam, lang));
    }

    @Operation(summary = "식당 메뉴 정보 조회", description = "식당의 특정 카테고리 하위 메뉴 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "식당의 특정 카테고리 하위 메뉴가 조회된다.",
                    content = @Content(schema = @Schema(implementation = RestaurantInfoResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "카레고리 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"카테고리 정보를 찾을 수 없습니다\"\"}")}
            ))
    })
    @GetMapping("/{restaurantId}/category/{menuCategoryId}/menu")
    public ApiResult<?> getRestaurantMenus(@PathVariable("restaurantId") Long restaurantId,
                                           @PathVariable("menuCategoryId") Long menuCategoryId,
                                           @RequestParam(name = "sort", required = false) String sortParam,
                                           @RequestParam(name = "lang", required = false, defaultValue = "kr") String lang) {

        return ApiResult.ok(HttpStatus.OK, restaurantService.getMenuListByLang(restaurantId, menuCategoryId, sortParam, lang));
    }

    @Operation(summary = "식당의 테이블 정보 조회", description = "식당의 모든 테이블 정보를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "식당의 특정 카테고리 하위 메뉴가 조회된다.",
                    content = @Content(schema = @Schema(implementation = RestaurantInfoResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "식당 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"식당 정보를 찾을 수 없습니다\"\"}")}
            ))
    })
    @GetMapping("/{restaurantId}/table")
    public ApiResult<?> getRestaurantTables(@PathVariable("restaurantId") Long restaurantId,
                                            @RequestParam(name="sort", required=false, defaultValue="table_id") String sortParam) {

        System.out.println("여기 통과");

        return ApiResult.ok(HttpStatus.OK, restaurantService.getTableList(restaurantId, sortParam));

    }
}
