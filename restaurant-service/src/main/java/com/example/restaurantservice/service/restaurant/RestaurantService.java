package com.example.restaurantservice.service.restaurant;

import com.example.restaurantservice.dto.MenuCategoryDto;
import com.example.restaurantservice.dto.MenuDto;
import com.example.restaurantservice.dto.TableDto;
import com.example.restaurantservice.dto.response.RestaurantCategoryInfoResponse;
import com.example.restaurantservice.dto.response.RestaurantInfoResponse;
import com.example.restaurantservice.dto.response.RestaurantMenuInfoResponse;
import com.example.restaurantservice.dto.response.RestaurantTableInfoResponse;
import com.example.restaurantservice.entity.menu.Menu;
import com.example.restaurantservice.entity.menu.MenuCategory;
import com.example.restaurantservice.entity.restaurant.Restaurant;
import com.example.restaurantservice.entity.restaurant.RestaurantTable;
import com.example.restaurantservice.exception.CustomException;
import com.example.restaurantservice.exception.ErrorCode;
import com.example.restaurantservice.repository.menu.MenuCategoryRepository;
import com.example.restaurantservice.repository.menu.MenuRepository;
import com.example.restaurantservice.repository.restaurant.RestaurantRepository;
import com.example.restaurantservice.repository.restaurant.RestaurantTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    public RestaurantCategoryInfoResponse getMenuCategories(Long restaurantId, String sortParam) {
        // 1) 식당 존재 여부 확인
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 식당을 찾을 수 없습니다."));

        // 2) 카테고리 목록 조회
        // 예: sortParam이 "displayOrder"라면 displayOrder 기준 정렬
        //     sortParam이 null 또는 다른 값이라면 그냥 기본 정렬(예: id 순)
        List<MenuCategory> categories;
        if ("displayOrder".equals(sortParam)) {
            categories = menuCategoryRepository.findByRestaurant_IdOrderByDisplayOrderAsc(restaurantId);
        } else {
            // 기본 정렬 (예: menuCategoryId asc) or 커스텀 로직
            categories = menuCategoryRepository.findByRestaurant_Id(restaurantId);
        }

        // 3) DTO 변환
        List<MenuCategoryDto> dtoList = categories.stream()
                .map(cat -> MenuCategoryDto.builder()
                        .menuCategoryId(cat.getId())
                        .menuCategoryName(cat.getMenuCategoryName())
                        .displayOrder(cat.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());

        // 4) 응답 구성
        return RestaurantCategoryInfoResponse.builder()
                .data(dtoList)
                .build();
    }

    public RestaurantCategoryInfoResponse getMenuCategoriesByLang(Long restaurantId, String sortParam, String lang) {

        // 1) 식당 존재 여부 확인
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 식당을 찾을 수 없습니다."));

        // 2) 카테고리 목록 조회
        // 예: sortParam이 "displayOrder"라면 displayOrder 기준 정렬
        //     sortParam이 null 또는 다른 값이라면 그냥 기본 정렬(예: id 순)
        List<MenuCategory> categories;
        if ("displayOrder".equals(sortParam)) {
            categories = menuCategoryRepository.findByRestaurant_IdOrderByDisplayOrderAsc(restaurantId);
        } else {
            // 기본 정렬 (예: menuCategoryId asc) or 커스텀 로직
            categories = menuCategoryRepository.findByRestaurant_Id(restaurantId);
        }

        // 3) DTO 변환
        List<MenuCategoryDto> dtoList = categories.stream()
                .map(category -> MenuCategoryDto.builder()
                        .menuCategoryId(category.getId())
                        .menuCategoryName(getCategoryNameByLang(category, lang)) // [CHANGED]
                        .displayOrder(category.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());

        return RestaurantCategoryInfoResponse.builder()
                .data(dtoList)
                .build();
    }

    // [CHANGED] lang에 따라 카테고리명 반환
    private String getCategoryNameByLang(MenuCategory cat, String lang) {

        switch (lang.toLowerCase()) {
            case "en":
                return (cat.getMenuCategoryNameEn() != null && !cat.getMenuCategoryNameEn().isEmpty())
                        ? cat.getMenuCategoryNameEn() : cat.getMenuCategoryName(); // fallback = 한글
            case "zh":
                return (cat.getMenuCategoryNameZh() != null && !cat.getMenuCategoryNameZh().isEmpty())
                        ? cat.getMenuCategoryNameZh() : cat.getMenuCategoryName();
            case "jp":
                return (cat.getMenuCategoryNameJp() != null && !cat.getMenuCategoryNameJp().isEmpty())
                        ? cat.getMenuCategoryNameJp() : cat.getMenuCategoryName();
            default:
                // "kr" or 기타
                return cat.getMenuCategoryName();
        }
    }

    public RestaurantInfoResponse getRestaurantInfo(Long restaurantId, Long tableId) {
        // 1) 식당 조회
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 2) 테이블 조회
        RestaurantTable table = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new CustomException(ErrorCode.TABLE_NOT_FOUND));

        return RestaurantInfoResponse.builder()
                .restaurantName(restaurant.getRestaurantName())
                .tableName(table.getTableName())
                .build();
    }

    public RestaurantMenuInfoResponse getMenuList(Long restaurantId, Long menuCategoryId, String sortParam) {

        // 2) 카테고리 존재 + 식당 소속 여부 확인
        MenuCategory category = menuCategoryRepository.findById(menuCategoryId)
                .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));

        // 3) 메뉴 목록 조회
        List<Menu> menus;
        if ("displayOrder".equals(sortParam)) {
            menus = menuRepository.findByRestaurant_IdAndMenuCategory_IdOrderByDisplayOrderAsc(restaurantId, menuCategoryId);
        } else {
            // 기본 정렬 (예: menu_id asc) or 다른 로직
            menus = menuRepository.findByRestaurant_IdAndMenuCategory_Id(restaurantId, menuCategoryId);
        }

        // 4) DTO 변환
        List<MenuDto> menuDtoList = menus.stream()
                .map(menu -> MenuDto.builder()
                        .menuId(menu.getId())
                        .menuName(menu.getMenuName())
                        .price(menu.getPrice())
                        .menuDescription(menu.getMenuDescription())
                        .menuImageUrl(menu.getMenuImageUrl())
                        .displayOrder(menu.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());

        return RestaurantMenuInfoResponse.builder()
                .data(menuDtoList)
                .build();
    }

    public RestaurantMenuInfoResponse getMenuListByLang(Long restaurantId, Long menuCategoryId, String sortParam, String lang) {

        // 2) 카테고리 존재 + 식당 소속 여부 확인
        MenuCategory category = menuCategoryRepository.findById(menuCategoryId)
                .orElseThrow(() -> new RuntimeException("해당 카테고리를 찾을 수 없습니다."));

        // 3) 메뉴 목록 조회
        List<Menu> menus;
        if ("displayOrder".equals(sortParam)) {
            menus = menuRepository.findByRestaurant_IdAndMenuCategory_IdOrderByDisplayOrderAsc(restaurantId, menuCategoryId);
        } else {
            // 기본 정렬 (예: menu_id asc) or 다른 로직
            menus = menuRepository.findByRestaurant_IdAndMenuCategory_Id(restaurantId, menuCategoryId);
        }

        // 4) DTO 변환
        List<MenuDto> menuDtoList = menus.stream()
                .map(menu -> MenuDto.builder()
                        .menuId(menu.getId())
                        .menuName(getMenuNameByLang(menu, lang)) // [CHANGED]
                        .price(menu.getPrice())
                        .menuDescription(getMenuDescByLang(menu, lang)) // [CHANGED]
                        .menuImageUrl(menu.getMenuImageUrl())
                        .displayOrder(menu.getDisplayOrder())
                        .build())
                .collect(Collectors.toList());

        return RestaurantMenuInfoResponse.builder()
                .data(menuDtoList)
                .build();
    }

    // [CHANGED] lang에 따라 메뉴명 반환
    private String getMenuNameByLang(Menu m, String lang) {

        switch (lang.toLowerCase()) {
            case "en":
                return (m.getMenuNameEn() != null && !m.getMenuNameEn().isEmpty())
                        ? m.getMenuNameEn() : m.getMenuName();
            case "zh":
                return (m.getMenuNameZh() != null && !m.getMenuNameZh().isEmpty())
                        ? m.getMenuNameZh() : m.getMenuName();
            case "jp":
                return (m.getMenuNameJp() != null && !m.getMenuNameJp().isEmpty())
                        ? m.getMenuNameJp() : m.getMenuName();
            default:
                // kr or 기타
                return m.getMenuName();
        }
    }

    // [CHANGED] lang에 따라 메뉴설명 반환
    private String getMenuDescByLang(Menu m, String lang) {

        switch (lang.toLowerCase()) {
            case "en":
                return (m.getMenuDescriptionEn() != null && !m.getMenuDescriptionEn().isEmpty())
                        ? m.getMenuDescriptionEn() : m.getMenuDescription();
            case "zh":
                return (m.getMenuDescriptionZh() != null && !m.getMenuDescriptionZh().isEmpty())
                        ? m.getMenuDescriptionZh() : m.getMenuDescription();
            case "jp":
                return (m.getMenuDescriptionJp() != null && !m.getMenuDescriptionJp().isEmpty())
                        ? m.getMenuDescriptionJp() : m.getMenuDescription();
            default:
                return m.getMenuDescription();
        }
    }

    public RestaurantTableInfoResponse getTableList(Long restaurantId, String sortParam) {
        // 1) 식당 존재 여부 확인
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("해당 식당 정보를 찾을 수 없습니다."));

        // 2) 테이블 목록 조회 (정렬)
        //    여기서는 sortParam="table_id"인 경우만 구현 (확장 가능)
        List<RestaurantTable> tables;
        if ("table_id".equalsIgnoreCase(sortParam)) {
            tables = restaurantTableRepository.findAllByRestaurantIdOrderByIdAsc(restaurantId);
        } else {
            // fallback: 그냥 table_id asc
            tables = restaurantTableRepository.findAllByRestaurantIdOrderByIdAsc(restaurantId);
        }

        // 3) DTO 변환
        List<TableDto> tableDtos = tables.stream()
                .map(table -> TableDto.builder()
                        .tableId(table.getId())
                        .tableName(table.getTableName())
                        .build())
                .collect(Collectors.toList());

        return RestaurantTableInfoResponse.builder()
                .data(tableDtos)
                .build();
    }
}
