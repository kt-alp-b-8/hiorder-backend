package com.example.restaurantservice.dto.response;

import com.example.restaurantservice.dto.MenuDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantMenuInfoResponse {

    private List<MenuDto> data;  // 메뉴 목록
}
