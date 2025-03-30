package com.example.restaurantservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDto {
    private Long menuId;
    private String menuName;
    private int price;
    private String menuDescription;
    private String menuImageUrl;
    private Integer displayOrder;
}