package com.example.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuCategoryCreateOrUpdateDto {
    private Long menuCategoryId;        // 존재 시 업데이트, null 시 새로 등록?
    private String menuCategoryName;    // 한글
    // ... price, etc. -> not relevant for category
}
