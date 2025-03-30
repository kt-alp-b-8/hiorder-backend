package com.example.restaurantservice.dto.response;

import com.example.restaurantservice.dto.MenuCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantCategoryInfoResponse {

    private List<MenuCategoryDto> data; // 카테고리 목록
}

