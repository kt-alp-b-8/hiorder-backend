package com.example.restaurantservice.controller.menu;

import com.example.restaurantservice.controller.api.ApiResult;
import com.example.restaurantservice.dto.request.MenuCreateRequest;
import com.example.restaurantservice.dto.request.MenuUpdateRequest;
import com.example.restaurantservice.dto.response.MenuCreateResponse;
import com.example.restaurantservice.dto.response.MenuDeleteResponse;
import com.example.restaurantservice.dto.response.MenuUpdateResponse;
import com.example.restaurantservice.service.menu.MenuService;
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
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@Tag(name = "메뉴 API")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "메뉴 등록", description = "사장님이 메뉴를 등록한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성된 메뉴 정보가 반환된다.",
                    content = @Content(schema = @Schema(implementation = MenuCreateResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "레스토랑 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"식당 정보를 찾을 수 없습니다\"\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "카테고리 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"카테고리 정보를 찾을 수 없습니다\"\"}")}
            ))
    })
    @PostMapping("/{restaurantId}/menu")
    public ApiResult<?> createMenu(@PathVariable("restaurantId") Long restaurantId,
                                   @RequestBody MenuCreateRequest menuCreateRequest) {

        return ApiResult.ok(HttpStatus.CREATED, menuService.createMenu(restaurantId, menuCreateRequest));
    }

    @Operation(summary = "메뉴 삭제", description = "사장님이 메뉴를 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제된 메뉴 ID가 반환된다.",
                    content = @Content(schema = @Schema(implementation = MenuDeleteResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "레스토랑 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"메뉴를 찾을 수 없습니다\"\"}")}
            ))
    })
    @DeleteMapping("/{restaurantId}/menus/{menuId}")
    public ApiResult<?> deleteMenu(@PathVariable("restaurantId") Long restaurantId,
                                   @PathVariable("menuId") Long menuId) {

        return ApiResult.ok(HttpStatus.OK, menuService.deleteMenu(restaurantId, menuId));
    }

    @Operation(summary = "메뉴 수정", description = "사장님이 메뉴를 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "수정된 메뉴 정보가 반환된다.",
                    content = @Content(schema = @Schema(implementation = MenuUpdateResponse.class))),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "레스토랑 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"식당 정보를 찾을 수 없습니다\"\"}")}
            )),
            @ApiResponse(responseCode = "404", content = @Content(mediaType = "application/json",
                    examples = {@ExampleObject(name = "카테고리 정보가 없는 경우",
                            value = "{\"code\":404, \"message\":\"카테고리 정보를 찾을 수 없습니다\"\"}")}
            ))
    })
    @PutMapping("/{restaurantId}/menus/{menuId}")
    public ApiResult<?> updateMenu(@PathVariable("restaurantId") Long restaurantId,
                                   @PathVariable("menuId") Long menuId,
                                   @RequestBody MenuUpdateRequest menuUpdateRequest) {

        return ApiResult.ok(HttpStatus.OK, menuService.updateMenu(restaurantId, menuId, menuUpdateRequest));
    }

}

